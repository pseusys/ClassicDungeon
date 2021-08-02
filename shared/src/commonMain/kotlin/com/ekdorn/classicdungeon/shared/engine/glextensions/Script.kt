package com.ekdorn.classicdungeon.shared.engine.glextensions

import com.ekdorn.classicdungeon.shared.dependant.gl.*
import com.ekdorn.classicdungeon.shared.engine.generics.Assigned
import com.ekdorn.classicdungeon.shared.engine.maths.Color
import com.ekdorn.classicdungeon.shared.engine.maths.Matrix

internal object Script: Assigned {
    private const val VERTEX_SHADER =
        """
            #version 100
            
            uniform mat4 camera;
            uniform mat4 model;
            
            attribute vec2 position;
            attribute vec2 coordinates;
            
            varying vec2 fragment;
            
            void main () {
                gl_Position = camera * model * vec4(position, 0.0, 1.0);
                fragment = coordinates;
            }
        """

    // TODO: revise colors
    private const val FRAGMENT_SHADER =
        """
            #version 100
            
            precision mediump float;
            
            uniform vec4 ambient;
            uniform vec4 material;
            uniform sampler2D texture;
            
            varying vec2 fragment;
            
            void main () {
                gl_FragColor = texture2D(texture, fragment) * material + ambient;
            }
        """


    private val program = GLProgram()
    private val vertexShader = GLShader(GLShader.TYPE.VERTEX)
    private val fragmentShader = GLShader(GLShader.TYPE.FRAGMENT)

    private val position: GLAttribute
    private val coordinates: GLAttribute

    private val camera: GLUniform
    private val model: GLUniform
    private val ambient: GLUniform
    private val material: GLUniform
    private val texture: GLUniform

    init {
        prepareShader(vertexShader, VERTEX_SHADER)
        prepareShader(fragmentShader, FRAGMENT_SHADER)

        program.link()?.apply { throw Exception("Program link error: $this") }
        vertexShader.delete()
        fragmentShader.delete()

        position = GLAttribute(program, "position")
        coordinates = GLAttribute(program, "coordinates")

        camera = GLUniform(program, "camera")
        model = GLUniform(program, "model")
        ambient = GLUniform(program, "ambient")
        material = GLUniform(program, "material")
        texture = GLUniform(program, "texture")

        program.use()
        position.enable()
        coordinates.enable()
    }

    private fun prepareShader (shader: GLShader, code: String) {
        shader.prepare(code)?.apply {
            throw Exception("${if (shader == vertexShader) "VERTEX" else "FRAGMENT"} shader compile error:\n$this")
        }
        program.attach(shader)
    }


    fun setCamera (matrix: Matrix) = camera.value4m(matrix.values)

    fun setModel (matrix: Matrix) = model.value4m(matrix.values)

    fun setAmbient (color: Color) = ambient.value4f(color.r, color.g, color.b, color.a)

    fun setMaterial (color: Color) = material.value4f(color.r, color.g, color.b, color.a)

    fun setTexture (sampler: GLTexture) = texture.value1i(sampler.id)


    fun drawMultiple (textures: Int) {
        position.set(2, 0, 4)
        coordinates.set(2, 2, 4)
        Mapper.requestFor(textures)
        Mapper.buffer.bind()
        GLFunctions.drawElements(Mapper.elementsForTextures(textures))
    }

    fun drawSingle () = drawMultiple(1)


    override fun gameEnded () {
        position.disable()
        coordinates.disable()
        program.delete()
    }
}

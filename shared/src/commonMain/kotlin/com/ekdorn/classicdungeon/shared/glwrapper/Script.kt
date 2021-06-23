package com.ekdorn.classicdungeon.shared.glwrapper

import com.ekdorn.classicdungeon.shared.dependant.GLFunctions
import com.ekdorn.classicdungeon.shared.generics.Assigned
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Matrix
import com.ekdorn.classicdungeon.shared.maths.Rectangle

internal object Script: Assigned {
    private const val VERTEX_SHADER =
        """
            #version 110
            
            uniform mat4 camera;
            uniform mat4 model;
            
            attribute vec2 position;
            attribute vec2 coordinates;
            
            varying fragment;
            
            void main () {
                gl_Position = camera * model * vec4(position, 0.0, 0.0);
                fragment = coordinates;
            }
        """

    // TODO: revise colors
    private const val FRAGMENT_SHADER =
        """
            #version 110
            
            uniform vec4 ambient;
            uniform vec4 material;
            uniform sampler2D texture;
            
            varying fragment;
            
            void main () {
                gl_FragColor = texture2D(texture, fragment) * material + ambient;
            }
        """


    private val program: Int
    private val vertexShader: Int
    private val fragmentShader: Int

    private val position: Int
    private val coordinates: Int

    private val camera: Int
    private val model: Int
    private val ambient: Int
    private val material: Int
    private val texture: Int

    init {
        program = GLFunctions.Program.create()

        vertexShader = GLFunctions.Shader.create(GLFunctions.Shader.TYPE.VERTEX)
        prepareShader(vertexShader, VERTEX_SHADER)
        fragmentShader = GLFunctions.Shader.create(GLFunctions.Shader.TYPE.FRAGMENT)
        prepareShader(fragmentShader, FRAGMENT_SHADER)

        GLFunctions.Program.link(program)?.apply { throw Exception("Program link error: $this") }
        GLFunctions.Shader.delete(vertexShader)
        GLFunctions.Shader.delete(fragmentShader)

        position = GLFunctions.Value.getAttribute("position")
        coordinates = GLFunctions.Value.getAttribute("coordinates")

        camera = GLFunctions.Value.getUniform("camera")
        model = GLFunctions.Value.getUniform("model")
        ambient = GLFunctions.Value.getUniform("ambient")
        material = GLFunctions.Value.getUniform("material")
        texture = GLFunctions.Value.getUniform("texture")

        GLFunctions.Program.use(program)
        GLFunctions.Value.enable(position)
        GLFunctions.Value.enable(coordinates)
    }

    private fun prepareShader (shader: Int, code: String) {
        GLFunctions.Shader.source(shader, code)
        GLFunctions.Shader.compile(shader)?.apply { throw Exception("Shader compile error: $this") }
        GLFunctions.Program.attach(program, shader)
    }


    inline fun setCamera (matrix: Matrix) {
        GLFunctions.Value.value4m(camera, matrix.to4x4())
    }

    inline fun setModel (matrix: Matrix) {
        GLFunctions.Value.value4m(model, matrix.to4x4())
    }

    inline fun setAmbient (color: Color) {
        GLFunctions.Value.value4f(ambient, color.r, color.g, color.b, color.a)
    }

    inline fun setMaterial (color: Color) {
        GLFunctions.Value.value4f(material, color.r, color.g, color.b, color.a)
    }

    inline fun setTexture (sampler: Texture) {
        GLFunctions.Value.value1i(texture, sampler.id)
    }


    @kotlin.ExperimentalUnsignedTypes
    fun drawSingle (image: Rectangle, texture: Rectangle) {
        GLFunctions.Value.vertexArray(position,2, image.toPointsArray())
        GLFunctions.Value.vertexArray(coordinates,2, texture.toPointsArray())
        GLFunctions.drawElements(Mapper.INDICES.size, Mapper.INDICES)
    }


    override fun gameStarted () {}

    override fun gameEnded() {
        GLFunctions.Value.disable(position)
        GLFunctions.Value.disable(coordinates)
        GLFunctions.Program.unuse(program)
    }
}

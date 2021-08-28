package com.ekdorn.classicdungeon.shared.gl.extensions

import com.ekdorn.classicdungeon.shared.engine.generics.Assigned
import com.ekdorn.classicdungeon.shared.engine.maths.Color
import com.ekdorn.classicdungeon.shared.engine.maths.Matrix
import com.ekdorn.classicdungeon.shared.gl.wrapper.*


/**
 * Object, designed for accessing GL.
 * It supports initialization, drawing calls and resource freeing.
 */
internal object Script: Assigned {
    /**
     * GL vertex shader, limited to 100 version because of WebGL.
     * - camera - camera matrix.
     * - model - model matrix.
     * - position - position of fragment in 2D space.
     * - coordinates - coordinates of texture edge, associated with vertex.
     */
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

    /**
     * GL fragment shader, limited to 100 version because of WebGL.
     * TODO: revise colors.
     * - ambient - color that will be added to texture color.
     * - material - color that texture color will be multiplied by.
     * - texture - texture sampler.
     * - fragment - coordinates of this fragment on texture.
     */
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

    /**
     * Function, preparing shader: compiling it and attaching to program.
     * It also throws an exception if shader wasn't compiled correctly.
     * @param shader shader to compile and attach
     * @param code shader code
     */
    private fun prepareShader (shader: GLShader, code: String) {
        shader.prepare(code)?.apply {
            throw Exception("${if (shader == vertexShader) "VERTEX" else "FRAGMENT"} shader compile error:\n$this")
        }
        program.attach(shader)
    }


    /**
     * Setting methods for shader variables.
     */
    fun setCamera (matrix: Matrix) = camera.value4m(matrix.values)
    fun setModel (matrix: Matrix) = model.value4m(matrix.values)
    fun setAmbient (color: Color) = ambient.value4fv(color.value)
    fun setMaterial (color: Color) = material.value4fv(color.value)
    fun setTexture (sampler: GLTexture) = texture.value1i(sampler.id)


    /**
     * Method for drawing multiple textures at once.
     * @param textures number of textures to draw
     */
    fun drawMultiple (textures: Int) {
        position.set(2, 0, 4)
        coordinates.set(2, 2, 4)
        Mapper.requestFor(textures)
        Mapper.buffer.bind()
        GLFunctions.drawElements(Mapper.elementsForTextures(textures))
    }

    /**
     * Method for drawing single texture.
     */
    fun drawSingle () = drawMultiple(1)


    /**
     * Function, freeing resources of GL, triggered on game ended.
     */
    override fun gameEnded () {
        position.disable()
        coordinates.disable()
        program.delete()
    }
}

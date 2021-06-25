package com.ekdorn.classicdungeon.shared.glwrapper

import com.ekdorn.classicdungeon.shared.dependant.GLFunctions
import com.ekdorn.classicdungeon.shared.generics.Assigned
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Matrix
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.ui.WidgetUI

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
                gl_FragColor = texture2D(texture, fragment);// * material + ambient;
            }
        """


    private val program = GLFunctions.Program.create()
    private val vertexShader = GLFunctions.Shader.create(GLFunctions.Shader.TYPE.VERTEX)
    private val fragmentShader = GLFunctions.Shader.create(GLFunctions.Shader.TYPE.FRAGMENT)

    private val buffers = mutableMapOf<Int, GLFunctions.Buffer>()

    private val position: Int
    private val coordinates: Int

    private val camera: Int
    private val model: Int
    private val ambient: Int
    private val material: Int
    private val texture: Int

    init {
        prepareShader(vertexShader, VERTEX_SHADER)
        prepareShader(fragmentShader, FRAGMENT_SHADER)

        GLFunctions.Program.link(program)?.apply { throw Exception("Program link error: $this") }
        GLFunctions.Shader.delete(vertexShader)
        GLFunctions.Shader.delete(fragmentShader)

        position = GLFunctions.Value.getAttribute(program, "position")
        coordinates = GLFunctions.Value.getAttribute(program, "coordinates")

        camera = GLFunctions.Value.getUniform(program, "camera")
        model = GLFunctions.Value.getUniform(program, "model")
        ambient = GLFunctions.Value.getUniform(program, "ambient")
        material = GLFunctions.Value.getUniform(program, "material")
        texture = GLFunctions.Value.getUniform(program, "texture")

        GLFunctions.Program.use(program)
        GLFunctions.Value.enable(position)
        GLFunctions.Value.enable(coordinates)
    }

    private fun prepareShader (shader: Int, code: String) {
        GLFunctions.Shader.source(shader, code)
        GLFunctions.Shader.compile(shader)?.apply {
            throw Exception("${if (shader == vertexShader) "VERTEX" else "FRAGMENT"} shader compile error: $this")
        }
        GLFunctions.Program.attach(program, shader)
    }


    fun setCamera (matrix: Matrix) {
        GLFunctions.Value.value4m(camera, matrix.to4x4())
    }

    fun setModel (matrix: Matrix) {
        GLFunctions.Value.value4m(model, matrix.to4x4())
    }

    fun setAmbient (color: Color) {
        GLFunctions.Value.value4f(ambient, color.r, color.g, color.b, color.a)
    }

    fun setMaterial (color: Color) {
        GLFunctions.Value.value4f(material, color.r, color.g, color.b, color.a)
    }

    fun setTexture (sampler: Texture) {
        GLFunctions.Value.value1i(texture, sampler.id)
    }


    fun createBuffer (widget: WidgetUI, size: Int) {
        buffers[widget.hashCode()] = GLFunctions.Buffer(size)
    }

    fun updateBuffer (widget: WidgetUI, fromEach: Int, vararg dataSeq: DoubleArray) {
        if (!buffers.containsKey(widget.hashCode())) throw Exception("No buffer found for the widget $widget")
        val size = dataSeq.size * dataSeq[0].size
        buffers[widget.hashCode()]!!.let { buffer ->
            if (size > buffer.size) throw Exception("Buffer for the widget $widget is shorter than expected!")
            buffer.bind()
            println(DoubleArray(size) { dataSeq[(it / 2) % dataSeq.size][(it / 2) + (it % 2) - (it / 2) % dataSeq.size] })
            buffer.fill(DoubleArray(size) { dataSeq[(it / 2) % dataSeq.size][(it / 2) + (it % 2) - (it / 2) % dataSeq.size] })
        }
    }

    fun deleteBuffer (widget: WidgetUI) {
        if (!buffers.containsKey(widget.hashCode())) throw Exception("No buffer found for the widget $widget")
        buffers[widget.hashCode()]?.delete()
    }


    fun drawSingle (widget: WidgetUI) {
        if (!buffers.containsKey(widget.hashCode())) throw Exception("No buffer found for the widget $widget")
        buffers[widget.hashCode()]!!.bind()
        GLFunctions.Value.vertexArray(position, 2, 0, 4)
        GLFunctions.Value.vertexArray(coordinates, 2, 2, 4)
        println("drawing ${Mapper.INDICES.size}: ${Mapper.INDICES}")
        GLFunctions.drawElements(Mapper.INDICES.size, Mapper.INDICES)
    }


    override suspend fun gameStarted () {}

    override suspend fun gameEnded() {
        buffers.forEach { it.value.delete() }
        GLFunctions.Value.disable(position)
        GLFunctions.Value.disable(coordinates)
        GLFunctions.Program.delete(program)
    }
}

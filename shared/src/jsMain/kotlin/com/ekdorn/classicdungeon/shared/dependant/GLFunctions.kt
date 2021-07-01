package com.ekdorn.classicdungeon.shared.dependant

import context
import org.khronos.webgl.*

actual object GLFunctions {
    val programs = mutableListOf<WebGLProgram?>()
    val shaders = mutableListOf<WebGLShader?>()

    actual object Texture {
        val textures = mutableListOf<WebGLTexture?>()


        actual enum class FILTERING_MODE (val mode: Int) {
            NEAREST(WebGLRenderingContext.NEAREST), LINEAR(WebGLRenderingContext.LINEAR)
        }

        actual enum class WRAPPING_MODE (val mode: Int) {
            CLAMP(WebGLRenderingContext.CLAMP_TO_EDGE)
        }

        actual fun generate(): Int {
            textures.add(context.createTexture())
            return textures.size - 1
        }

        actual fun bind(texture: Int) {
            context.bindTexture(WebGLRenderingContext.TEXTURE_2D, textures[texture])
        }

        actual fun release(texture: Int) {}

        actual fun delete(texture: Int) {
            context.deleteTexture(textures[texture])
        }

        actual fun filter(minification: FILTERING_MODE, magnification: FILTERING_MODE) {
            context.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MIN_FILTER, minification.mode)
            context.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MAG_FILTER, magnification.mode)
        }

        actual fun wrap(s: WRAPPING_MODE, t: WRAPPING_MODE) {
            context.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_WRAP_S, s.mode)
            context.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_WRAP_T, t.mode)
        }

        actual fun image(w: Int, h: Int, data: ByteArray) {
            context.texImage2D(WebGLRenderingContext.TEXTURE_2D, 0, WebGLRenderingContext.RGBA, w, h, 0, WebGLRenderingContext.RGBA, WebGLRenderingContext.UNSIGNED_BYTE, Uint8Array(data.toTypedArray()))
        }

    }

    actual object Value {
        val uniforms = mutableListOf<WebGLUniformLocation?>()


        actual fun getAttribute(program: Int, name: String): Int {
            return context.getAttribLocation(programs[program], name)
        }

        actual fun getUniform(program: Int, name: String): Int {
            uniforms.add(context.getUniformLocation(programs[program], name))
            return uniforms.size - 1
        }

        actual fun enable(location: Int) {
            context.enableVertexAttribArray(location)
        }

        actual fun disable(location: Int) {
            context.disableVertexAttribArray(location)
        }

        actual fun value1i(location: Int, value: Int) {
            context.uniform1i(uniforms[location], value)
        }

        actual fun value4f(location: Int, value1: Double, value2: Double, value3: Double, value4: Double) {
            context.uniform4f(uniforms[location], value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat())
        }

        actual fun value4m(location: Int, value: DoubleArray) {
            context.uniformMatrix4fv(uniforms[location], false, value.map { it.toFloat() }.toTypedArray())
        }

        actual fun vertexArray(location: Int, size: Int, offset: Int, stride: Int) {
            context.vertexAttribPointer(location, size, WebGLRenderingContext.FLOAT, false, stride * Float.SIZE_BYTES, offset * Float.SIZE_BYTES)
        }

    }

    actual object Program {
        actual fun create(): Int {
            programs.add(context.createProgram())
            return programs.size - 1
        }

        actual fun use(program: Int) {
            context.useProgram(programs[program])
        }

        actual fun delete(program: Int) {
            context.deleteProgram(programs[program])
        }

        actual fun attach(program: Int, shader: Int) {
            context.attachShader(programs[program], shaders[shader])
        }

        actual fun link(program: Int): String? {
            context.linkProgram(programs[program])
            return if (context.getProgramParameter(programs[program], WebGLRenderingContext.LINK_STATUS) != true) {
                context.getProgramInfoLog(programs[program])
            } else null
        }

    }

    actual object Shader {
        actual enum class TYPE (val type: Int) {
            VERTEX(WebGLRenderingContext.VERTEX_SHADER), FRAGMENT(WebGLRenderingContext.FRAGMENT_SHADER)
        }

        actual fun create(type: TYPE): Int {
            shaders.add(context.createShader(type.type))
            return shaders.size - 1
        }

        actual fun source(shader: Int, code: String) {
            context.shaderSource(shaders[shader], code)
        }

        actual fun compile(shader: Int): String? {
            context.compileShader(shaders[shader])
            return if (context.getShaderParameter(shaders[shader], WebGLRenderingContext.COMPILE_STATUS) != true) {
                context.getShaderInfoLog(shaders[shader])
            } else null
        }

        actual fun delete(shader: Int) {
            context.deleteShader(shaders[shader])
        }
    }

    val buffers = mutableListOf<WebGLBuffer?>()
    actual class Buffer actual constructor(actual val size: Int) {
        internal val id: Int

        init {
            buffers.add(context.createBuffer())
            id = buffers.size - 1
        }

        actual fun bind() {
            context.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, buffers[id])
        }

        actual fun fill(data: DoubleArray) {
            context.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array(data.map { it.toFloat() }.toTypedArray()), WebGLRenderingContext.DYNAMIC_DRAW)
        }

        actual fun delete() {
            context.deleteBuffer(buffers[id])
        }
    }


    actual fun setup () {
        context.clearColor(0.0F, 0.0F, 0.0F, 1.0F)
        context.pixelStorei(WebGLRenderingContext.UNPACK_FLIP_Y_WEBGL, 1)
    }

    actual fun drawElements(count: Int, indices: ByteArray) {
        context.clear(WebGLRenderingContext.DEPTH_BUFFER_BIT or WebGLRenderingContext.COLOR_BUFFER_BIT)
        val buff = context.createBuffer()
        context.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, buff)
        context.bufferData(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, Int8Array(indices.toTypedArray()), WebGLRenderingContext.STATIC_DRAW)
        context.drawElements(WebGLRenderingContext.TRIANGLES, count, WebGLRenderingContext.UNSIGNED_BYTE, 0)
        context.deleteBuffer(buff)
    }
}

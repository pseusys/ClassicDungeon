package com.ekdorn.classicdungeon.shared.dependant


expect object GLFunctions {
    // TODO: create texture class as well as Attribute, Uniform, Buffer, etc.
    object Texture {
        enum class FILTERING_MODE {
            NEAREST, LINEAR
        }

        // which ones needed?
        enum class WRAPPING_MODE {
            CLAMP
        }

        fun generate(): Int
        //fun activate(texture: Int)
        fun bind(texture: Int)
        fun release(texture: Int)
        fun delete(texture: Int)

        fun filter(minification: FILTERING_MODE, magnification: FILTERING_MODE)
        fun wrap(s: WRAPPING_MODE, t: WRAPPING_MODE)

        fun image(w: Int, h: Int, data: ByteArray)
    }

    object Value {
        fun getAttribute (program: Int, name: String): Int
        fun getUniform (program: Int, name: String): Int

        // For attributes only (separate?)
        fun enable (location: Int)
        fun disable (location: Int)

        // For uniforms only
        fun value1i (location: Int, value: Int)
        fun value4f (location: Int, value1: Double, value2: Double, value3: Double, value4: Double)
        fun value4m (location: Int, value: DoubleArray)

        // For attributes only
        fun vertexArray (location: Int, size: Int, offset: Int, stride: Int)

        //TODO: all to float?
    }

    object Program {
        fun create (): Int
        fun use (program: Int)
        fun delete (program: Int)

        fun attach (program: Int, shader: Int)
        fun link (program: Int): String?
    }

    object Shader {
        enum class TYPE {
            VERTEX, FRAGMENT
        }

        fun create (type: TYPE): Int
        fun source (shader: Int, code: String)
        fun compile (shader: Int): String?
        fun delete (shader: Int)
    }

    class Buffer (size: Int) {
        val size: Int

        fun bind ()
        fun fill (data: DoubleArray)
        fun delete ()
    }


    fun drawElements (count: Int, indices: ByteArray)
}
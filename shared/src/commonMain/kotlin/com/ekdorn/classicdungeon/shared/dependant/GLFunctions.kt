package com.ekdorn.classicdungeon.shared.dependant


expect object GLFunctions {
    object Texture {
        enum class FILTERING_MODE {
            NEAREST, LINEAR
        }

        enum class WRAPPING_MODE {
            NEAREST, LINEAR
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
        fun getAttribute (name: String): Int
        fun getUniform (name: String): Int

        fun enable (location: Int)
        fun disable (location: Int)

        fun value1i (location: Int, value: Int)
        fun value4f (location: Int, value1: Double, value2: Double, value3: Double, value4: Double)
        fun value4m (location: Int, value: DoubleArray)

        fun vertexArray (location: Int, size: Int, value: DoubleArray)
    }

    object Program {
        fun create (): Int
        fun use (program: Int)
        fun unuse (program: Int)

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


    @kotlin.ExperimentalUnsignedTypes
    fun drawElements (count: Int, indices: UByteArray)
}

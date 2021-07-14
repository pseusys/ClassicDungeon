package com.ekdorn.classicdungeon.shared.engine.lib


internal typealias Listener = () -> Unit



// Target - Result
internal typealias TRListener <Target, Result> = (Target) -> Result

// Target Cancellable
internal typealias TCListener <Target> = TRListener<Target, Boolean>

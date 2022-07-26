package com.ekdorn.classicdungeon.shared.engine.utils


/**
 * Plain listener - no args and Unit as return type.
 */
internal typealias Listener = () -> Unit



/**
 * Target Result listener - has Target as argument and Result as return type.
 */
internal typealias TRListener <Target, Result> = (Target) -> Result

/**
 * Target Cancellable listener - has Target as argument and returns boolean for cancelling possibility.
 */
internal typealias TListener <Target> = TRListener<Target, Unit>

/**
 * Target Cancellable listener - has Target as argument and returns boolean for cancelling possibility.
 */
internal typealias TCListener <Target> = TRListener<Target, Boolean>

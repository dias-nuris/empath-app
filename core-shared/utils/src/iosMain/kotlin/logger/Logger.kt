package logger

import co.touchlab.kermit.Logger

actual class Logger: BaseLogger {

    actual override fun d(tag: String, message: String) =
        Logger.d(tag = tag, null, message = {message})

    actual override fun e(tag: String, message: String) =
        Logger.e(tag = tag, null, message = {message})

}
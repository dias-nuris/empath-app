package logger

expect class Logger(): BaseLogger {
    override fun d(tag: String, message:String)
    override fun e(tag: String, message:String)
}

interface BaseLogger {
    fun d(tag: String, message:String)
    fun e(tag: String, message: String)
}

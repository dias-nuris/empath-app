package platform

expect class Platform() {
    val type: PlatformType
}
enum class PlatformType{
    IOS,
    ANDROID
}
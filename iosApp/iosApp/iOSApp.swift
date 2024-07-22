import SwiftUI
import SharedDi
import SharedUtils

@main
struct iOSApp: App {
    
    init(){
        IosModuleKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

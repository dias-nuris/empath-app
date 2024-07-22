package di

import com.example.project.BuildKonfig
import network.*
import org.koin.dsl.module
import token.manager.TokenBaseSessionManager

val networkModule = module {
    factory { provideJson() }
    factory {
        provideHttpClient(
            json = get(),
            customLogger = get(),
            sessionManager = get<TokenBaseSessionManager>(),
        )
    }
    factory {
        provideKtorHttpClient(
            httpClient = get(),
            baseUrl = BuildKonfig.BASE_URL,
        )
    }
}

import data.network.AuthApi
import data.network.createAuthApi
import data.repository.AuthRepositoryImpl
import de.jensklingenberg.ktorfit.Ktorfit
import domain.repository.AuthRepository
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUserUseCase
import domain.usecase.SendEmailCodeUseCase
import domain.usecase.VerifyEmailUseCase
import org.koin.dsl.bind
import org.koin.dsl.module

val authApiModule = module {
    factory { get<Ktorfit>().createAuthApi() }
    factory { AuthRepositoryImpl(api = get<AuthApi>()) }
    single { LoginUseCase(repository = get()) }
    single { RegisterUserUseCase(repository = get()) }
    single { SendEmailCodeUseCase(repository = get()) }
    single { VerifyEmailUseCase(repository = get()) }
}
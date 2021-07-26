package com.example.stomone.dagger



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.viewmodels.*
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SetPasswordViewModel::class)
    abstract fun bindSetPasswordViewModel(fragmentViewModel: SetPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ActivationDatabaseViewModel::class)
    abstract fun bindActivationDatabaseViewModel(favoritesFilmsViewModel: ActivationDatabaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactInformationViewModel::class)
    abstract fun bindReminderFragmentViewModel(favoritesFilmsViewModel: ContactInformationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContractsViewModel::class)
    abstract fun bindContractsViewModel(favoritesFilmsViewModel: ContractsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VisitHistoryViewModel::class)
    abstract fun bindVisitHistoryViewModel(favoritesFilmsViewModel: VisitHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(XRaysViewModel::class)
    abstract fun bindXRaysViewModel(favoritesFilmsViewModel: XRaysViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PicturesVisitViewModel::class)
    abstract fun bindPicturesVisitViewModel(favoritesFilmsViewModel: PicturesVisitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RadiationDoseViewModel::class)
    abstract fun bindRadiationDoseViewModel(favoritesFilmsViewModel: RadiationDoseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewPhotoViewModel::class)
    abstract fun bindViewPhotoViewModel(favoritesFilmsViewModel: ViewPhotoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListDepartmentViewModel::class)
    abstract fun bindListDepartmentViewModel(favoritesFilmsViewModel: ListDepartmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfficeHoursViewModel::class)
    abstract fun bindListOfficeHoursViewModel(favoritesFilmsViewModel: OfficeHoursViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BusinessHoursViewModel::class)
    abstract fun bindListBusinessHoursViewModel(favoritesFilmsViewModel: BusinessHoursViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AppointmentViewModel::class)
    abstract fun bindListAppointmentViewModel(favoritesFilmsViewModel: AppointmentViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
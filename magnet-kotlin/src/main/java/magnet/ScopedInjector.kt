package magnet

class ScopedInjector {

    lateinit var scope: Scope

    inline fun <reified T> single(classifier: String = Classifier.NONE): Lazy<T> =
        lazySingle(this, T::class.java, classifier)

    inline fun <reified T> optional(classifier: String = Classifier.NONE): Lazy<T?> =
        lazyOptional(this, T::class.java, classifier)

    inline fun <reified T> many(classifier: String = Classifier.NONE): Lazy<List<T>> =
        lazyMany(this, T::class.java, classifier)

    internal fun isScopeInitialized() = ::scope.isInitialized

    companion object {
        fun <T> lazySingle(injector: ScopedInjector, type: Class<T>, classifier: String): Lazy<T> =
            LazySingle(injector, type, classifier)

        fun <T> lazyOptional(injector: ScopedInjector, type: Class<T>, classifier: String): Lazy<T?> =
            LazyOptional(injector, type, classifier)

        fun <T> lazyMany(injector: ScopedInjector, type: Class<T>, classifier: String): Lazy<List<T>> =
            LazyMany(injector, type, classifier)
    }

}

internal class LazySingle<T>(
    private val injector: ScopedInjector,
    private val type: Class<T>,
    private val classifier: String
) : Lazy<T> {
    override val value: T get() = injector.scope.getSingle(type, classifier)
    override fun isInitialized() = injector.isScopeInitialized()
}

internal class LazyOptional<T>(
    private val injector: ScopedInjector,
    private val type: Class<T>,
    private val classifier: String
) : Lazy<T?> {
    override val value: T? get() = injector.scope.getOptional(type, classifier)
    override fun isInitialized() = injector.isScopeInitialized()
}

internal class LazyMany<T>(
    private val injector: ScopedInjector,
    private val type: Class<T>,
    private val classifier: String
) : Lazy<List<T>> {
    override val value: List<T> get() = injector.scope.getMany(type, classifier)
    override fun isInitialized() = injector.isScopeInitialized()
}

class D1
class D2

class Tester {

    val injector = ScopedInjector()

    val value1: String by injector.single()
    val value2: D1? by injector.optional()
    val value3: List<D2> by injector.many()

}

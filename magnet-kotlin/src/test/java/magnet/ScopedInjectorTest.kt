package magnet

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@Suppress("UNUSED_VARIABLE")
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class ScopedInjectorTest {

    @Mock
    lateinit var scope: Scope
    lateinit var injector: ScopedInjector

    @Before
    fun before() {
        injector = ScopedInjector()
        injector.scope = scope
    }

    @Test
    fun test1() {
        val test = Tester().also { it.injector.scope = scope }

        println(test.value1)
        println(test.value1)
        println(test.value1)

        //verify(scope, times(1)).getSingle(String::class.java, Classifier.NONE)
    }

}
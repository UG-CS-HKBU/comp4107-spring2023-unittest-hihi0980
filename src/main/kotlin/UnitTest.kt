import junit.framework.Assert.assertTrue
import org.junit.Test

/// Lines of the production code
val heros = mutableListOf<Hero>()

@Test
fun testCaoDodgeAttack(){
    var monarchHero = CaoCao()
    for (i in 0..6) /// assume that there are total 7 heros
        heros.add(NoneMonarchFactory.createRandomHero())
    assertTrue(monarchHero.dodgeAttack())
}

@Test
fun testBeingAttacked_ZhangFei() {
    val hero = ZhangFei(MinisterRole())
    val spy = object: WarriorHero(MinisterRole()) {
        override val name = hero.name
        override fun beingAttacked() {
            hero.beingAttacked()
            assertTrue(hero.hp >= 0)
        }
    }
    for(i in 0..10)
        spy.beingAttacked()
}

object FakeFactory: GameObjectFactory {
    var count = 0
    var last: WeiHero? = null
    init {
        monarchHero = CaoCao()
    }
    override fun getRandomRole(): Role =
        MinisterRole()
    override fun createRandomHero(): Hero {
        val hero = when(count++) {
            0->SimaYi(getRandomRole())
            else->XuChu(getRandomRole())
        }
        val cao = monarchHero as CaoCao
        if (last == null)
            cao.helper = hero
        else
            last!!.setNext(hero)
        last = hero
        return hero
    }
}

class DummyRole() : Role{
    override val roleTitle: String = ""
    override fun getEnemy(): String {
        return ""
    }
}

@Test
fun testBeingAttacked_ZhangFei(id : Int) {
    val dummy = DummyRole()
    val hero = ZhangFei(dummy)
    val spy = object: WarriorHero(MinisterRole()) {
        override val name = hero.name
        override fun beingAttacked() {
            hero.beingAttacked()
            assertTrue(hero.hp >= 0)
        }
    }
    for(i in 0..10)
        spy.beingAttacked()
}
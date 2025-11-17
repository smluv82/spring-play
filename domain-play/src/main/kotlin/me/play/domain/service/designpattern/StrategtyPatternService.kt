package me.play.domain.service.designpattern

import mu.KotlinLogging


private val log = KotlinLogging.logger { }

/**
 * 나는 행위 인터페이스
 */
interface FlyBehavior {
    val message: String

    fun fly(): String {
        log.info { message }
        return message
    }
}

class FlyWithWings(override val message: String = "날고 있어요") : FlyBehavior

class FlyNoWay(override val message: String = "날 수 없어요!!") : FlyBehavior

class FlyWithRocket(override val message: String = "로켓으로 날아가요") : FlyBehavior

/**
 * 소리 행위 인터페이스
 */
interface QuackBehavior {
    val message: String

    fun quack(): String {
        log.info { message }
        return message
    }
}

class Quack(override val message: String = "꽥꽥") : QuackBehavior


class MuteQuack(override val message: String = "조용해") : QuackBehavior


abstract class Duck(
    protected var flyBehavior: FlyBehavior,
    protected var quackBehavior: QuackBehavior
) {
    abstract fun display(): String

    fun playFly() = flyBehavior.fly()

    fun playQuack() = quackBehavior.quack()

    fun playSwim() = "오리는 수영 잘해"

    fun changeFlyBehavior(newFlyBehavior: FlyBehavior) {
        this.flyBehavior = newFlyBehavior
    }

    fun changeQuackBehavior(newQuackBehavior: QuackBehavior) {
        this.quackBehavior = newQuackBehavior
    }
}

class MallardDuck : Duck(
    flyBehavior = FlyWithWings(),
    quackBehavior = Quack()
) {
    override fun display(): String = "물오리"
}

class ModelDuck : Duck(
    flyBehavior = FlyNoWay(), // 처음에는 날 수 없는 모델 오리
    quackBehavior = MuteQuack()
) {
    override fun display(): String = "모델 오리"
}

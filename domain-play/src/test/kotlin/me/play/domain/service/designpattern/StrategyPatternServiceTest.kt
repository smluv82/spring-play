package me.play.domain.service.designpattern

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class StrategyPatternServiceTest {

    @Test
    fun `MallardDuck_행위_검증_테스트`() {
        // Given
        val mallard = MallardDuck()

        // When
        val flyResult = mallard.playFly()
        val quackResult = mallard.playQuack()
        val swimResult = mallard.playSwim()

        // Then
        assertEquals("물오리", mallard.display(), "오리 타입 확인")
        assertEquals("날고 있어요", flyResult, "날기 행위 검증")
        assertEquals("꽥꽥", quackResult, "꽥꽥 행위 검증")
        assertEquals("오리는 수영 잘해", swimResult, "수영 행위는 변하지 않음")
    }

    @Test
    fun `ModelDuck_동적_행위_변경_테스트`() {
        // Given
        val model = ModelDuck()

        // 1. 초기 행위 검증
        assertEquals("모델 오리", model.display())
        assertEquals("날 수 없어요!!", model.playFly(), "초기에는 날 수 없음")
        assertEquals("조용해", model.playQuack(), "초기에는 조용함")

        // 2. 런타임에 날기 전략을 변경 (로켓 장착!)
        model.changeFlyBehavior(FlyWithRocket())

        // 3. 변경된 행위 검증
        val newFlyResult = model.playFly()
        assertEquals("로켓으로 날아가요", newFlyResult, "행위가 로켓으로 변경됨")

        // 4. 꽥꽥 행위는 그대로인지 확인
        assertEquals("조용해", model.playQuack(), "꽥꽥 행위는 변경되지 않음")
    }
}
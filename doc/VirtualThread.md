# VirtualThread

1. 기존 Thread 개념 및 한계

- 기존 자바 Thread는 JVM에서 사용하는 Platform Thread와 OS 커널의 OS Thread와 1:1 매핑
- 기존 Spring Web을 통한 1개의 Request는 1개의 Thread를 점유하여 처리하였다. (Thread Per Request : Servlet 기반 동기 서버 모델에서는 요청당 1개의 스레드가 배정됨)
- 요청마다 쓰레드를 매번 생성할 수 없기 때문에 쓰레드를 미리 만들어둔 스레드로 처리 하도록 하기위해 Thread Pool에 미리 쓰레드들을 생성함.
- 문제점은 서비스가 커지면서 request의 요청이 많아질 때 서비스 처리량을 늘리기 위해서는 스레드 개수를 늘리는 것이지만 이것은 비용적인 측면과 공간적인 한계가 있음.
- 스레드간 전환 시 Context Switching 비용이 사용되어지며, I/O등의 작업등으로 인해 Blocking되면 매핑된 OS 커널 스레드까지 함께 Blocking되어 비효율적
- 위에서 말하는 Blocking은 흔히 I/O 작업이라고 이해하면 되고, 예로 DB 조회, 외부 API 호출등으로 이해하면 됨.
- 위의 단점을 극복하고자 리액티브(Netty) 같은 비동기 처리 방식을 이용할수 있지만, 구현의 복잡도 및 디버깅 어려움과 러닝커브가 높다.

2. Virtual Thread 개념

- 위의 한계점을 극복하기 위해 JDK19에서 추가되었으며, 기존의 JVM Thread : OS Thread의 1:1 매핑에서 벗어남.
- JVM 내부에서 Virtual Thread을 추가하고 JVM Thread (Carrier Thread)와 M:N 관계로 매핑되어짐. (단순 1:n이 아니라 M개의 Virtual Thread가 N개의 Carrier
  Thread 위에서 스케줄링 됨) (carrier : kernel은 기존과 동일하게 1:1로 매핑)
- 즉, 경량화된 쓰레드로 JVM 이 관리하는 경량 스레드 모델
- 기존 Thread의 문제점인 Blocking 되면, 커널 쓰레드까지 블록킹 되던 부분이 Virtual Thread에서 블록킹이 발생하면, Carrier Thread에서 Unmount되고, 다른 Virtual
  thread가 그자리에 mount 되어짐. 따라서 OS Kernel Thread까지 블록킹이 되지 않아 효율적으로 자원을 사용한다.

3. Virtual Thread 장점

- 프로그래머는 기존 동기/블로킹 코드를 그대로 작성하면서도, Virtual Thread를 사용하면 고성능을 낼 수 있음.
- 수십만~수백만 수준의 스레드 생성 및 실행할 수 있어 대규모 동시성을 쉽게 처리 가능.

4. Virtual Thread 동작 방식 (Carrier Thread, 스케줄링)

- ForkJoinPool 기반의 ForkJoinWorkerThreadFactory 전용 스케줄러를 통해 Carrier Thread를 관리함.
- Virtual Thread는 Carrier Thread의 WorkQueue에 work-stealing 방식으로 mount 되어 실행
- work-stealing이란 만약 Carrier Thread가 쉬고 있으면 다른 스레드의 WorkQueue에 있는 task를 가져와서 실행하는 방식
- mount된 Virtual Thread는 실행 중 Blocking되면 현재까지의 실행정보 (스택 프레임)을 힙에 저장하고 다른 Virtual Thread로 변경되어진다. (Context Switching)
- Blocking이 끝난 Virtual Thread는 mount될 때 힙에 저장해둔 스택 프레임을 불러와 중단 지점부터 작업을 재개함.


5. 코드

- VirtualThreadTest.kt




### 동기와 비동기
#### 함수 호출 관점
|                동기                |                비동기                 |
|:--------------------------------:|:----------------------------------:|
|  `caller`는 `callee`의 결과에 관심이 있다  |   `caller`는 `callee`의 결과에 관심이 없다   |
| `caller`는 결과를 이용해서 action 을 수행한다 | `callee`는 결과를 이용해서 callback 을 수행한다 |

### Blocking 과 Non-blocking
#### 함수 호출 관점
|             Blocking              |             Non-blocking              |
|:---------------------------------:|:-------------------------------------:|
| `caller`는 `callee`가 완료될 때까지 대기한다  | `caller`는 `callee`를 기다리지 않고 본인의 일을 한다 |
| `caller`와 다른 별도의 thread 가 필요하지 않다 |    `callee`와 다른 별도의 thread 가 필요하다     |


### CompletionStage
#### `thenAccept`
- `Consumer` 를 파라미터로 받는다
- 이전 task 로부터 값을 받지만 값을 넘기지 않는다
- 다음 task 에게 `null`이 전달된다
- 값을 받아서 `action` 만 수행하는 경우 유용

```
public static void main(String[] args) {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.finishedStage();
        stage.thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync", i);
        }).thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync2", i);
        });
        log.info("after thenAcceptAsync");
    }
```

```
[main] - start main
[ForkJoinPool.commonPool-worker-19] - return in future
[main] - after thenAccept
[ForkJoinPool.commonPool-worker-19] - 1 in thenAcceptAsync
[ForkJoinPool.commonPool-worker-5] - null in thenAcceptAsync2
```

#### `thenApply`
- `Function` 를 파라미터로 받는다
- 이전 task 로부터 T 타입의 값을 받아서 가공하고 U 타입의 값을 반환한다
- 다음 task 에게 반환값이 전달된다
- 값을 변형해서 전달해야 하는 경우 유용

```
public static void main(String[] args) {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenApplyAsync(value ->{
            var next = value +1;
            return next;
        }).thenApplyAsync(value ->{
            var next = "result: " + value;
            return next;
        }).thenApplyAsync(value -> {
            var next = value.equals("result: 2");
            return next;
        }).thenAcceptAsync(value -> log.info("{}", value));
    }
```

```
[ForkJoinPool.commonPool-worker-19] - return in future
[ForkJoinPool.commonPool-worker-19] - in thenApplyAsync: 2
[ForkJoinPool.commonPool-worker-19] - in thenApplyAsync2: result: 2
[ForkJoinPool.commonPool-worker-19] - in thenApplyAsync3: true
[ForkJoinPool.commonPool-worker-19] - true
```


#### `thenCompose`
- `Function` 를 파라미터로 받는다
- 이전 task 로부터 T 타입의 값을 받아서 가공하고 U 타입의 `CompletionStage`를 반환한다
- 반환한 `CompletionStage` 가 done 상태가 되면 값을 다음 task 에 전달한다
- 다른 `Future` 을 반환해야 하는 경우 유용

```
public static void main(String[] args) throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenComposeAsync(value -> {
            var next = Helper.addOne(value);
            log.info("in thenComposeAsync: {}", next);
            return next;
        }).thenComposeAsync(value -> {
            var next = Helper.addResultPrefix(value);
            log.info("in thenComposeAsync2: {}", next);
            return next;
        }).thenAcceptAsync(value -> {
            log.info("{} in thenAsync", value);
        });
        Thread.sleep(1000);
    }
```

```
19:14:48 [main] - start main
19:14:48 [ForkJoinPool.commonPool-worker-19] - I'm running!
19:14:48 [ForkJoinPool.commonPool-worker-19] - in thenComposeAsync:
java.util.concurrent.CompletableFuture@37b05857[Not completed]
19:14:48 [ForkJoinPool.commonPool-worker-19] - in thenComposeAsync2:
java.util.concurrent.CompletableFuture@6398d2c5[Not completed]
19:14:48 [ForkJoinPool.commonPool-worker-5] - result: 2 in thenAcceptAsync
```


#### `thenRun`
- `Runnable` 를 파라미터로 받는다
- 이전 task 로부터 값을 받지 않고 값을 반환하지 않는다
- 다음 task에게 `null`이 전달된다
- `Future`가 완료되었다는 이벤트를 기록할 때 유용

```
ppublic static void main(String[] args) throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenRunAsync(() -> {
            log.info("in theRunAsync");
        }).thenRunAsync(() -> {
            log.info("in theRunAsync");
        }).thenAcceptAsync(value -> {
            log.info("{} in thenAcceptAsync", value);
        });
        Thread.sleep(100);
    }
```

```
48:32 [main] - start main
48:32 [ForkJoinPool.commonPool-worker-19] - return in future
48:32 [ForkJoinPool.commonPool-worker-19] - in thenRunAsync
48:32 [ForkJoinPool.commonPool-worker-19] - in thenRunAsync2
48:32 [ForkJoinPool.commonPool-worker-19] - null in thenAcceptAsync
```

#### `exceptionally`
- `Function` 을 파라미터로 받는다
- 이전 task 에서 발생한 exception 을 받아서 처리하고 값을 반환한다
- 다음 task에게 반환된 값이 전달한다
- `Future` 파이프에서 발생한 에러를 처리할 때 유용

```
public static void main(String[] args) throws InterruptedException {
        Helper.completionStage()
                .thenApplyAsync(i -> {
                    log.info("in thenAsync");
                    return i/0;
                }).exceptionally(e -> {
                    log.info("{} in exceptionally", e.getMessage());
                    return 0;
                }).thenAcceptAsync(value -> {
                    log.info("{} in thenAcceptAsync", value);
                });

        Thread.sleep(1000);
    }
```

```
55:36 [ForkJoinPool.commonPool-worker-19] - return in future
55:36 [ForkJoinPool.commonPool-worker-5] - in thenApplyAsync
55:36 [ForkJoinPool.commonPool-worker-5] -
java.lang.ArithmeticException: / by zero in exceptionally
55:36 [ForkJoinPool.commonPool-worker-5] - 0 in thenAcceptAsync
```

### Reactive Programming
- 비동기 데이터 stream 을 사용하는 패러다임
- event-driven
  - 모든 것이 이벤트로 구성되고 이벤트를 통해서 전파되어야 한다.
  - 데이터의 전달, 에러, 완료까지 모두 이벤트로 취급
  - event-driven 은 message-driven 에 속한다.
- Reactive Manifesto 의 Responsive, Resilient, Elastic, Message Driven 까지 모두 해당
#### Reactive Manifesto
> - 구성 요소는 서로 비동기적으로 메시지를 주고 받으며, 독립적인 실행을 보장한다.
> - 메시지 큐를 생성하고 배압을 적용하여 부하를 관리하고 흐름을 제어한다.

#### Reactive Stream
- `Callee`는 `Caller`에게 응답이 아닌 `Publisher`를 전달한다.
  - `Publisher`는 메세지 큐를 생성해서 부하를 관리하고 흐름을 제어한다.
  
    ➡️ **back-pressure**를 조절할 수 있는 수단
- `Callee`는 각각의 값을 `Publisher`를 통해서 전달한다.
- `Caller`는 해당 `Publisher`를 `Subscribe` 하거나 다른 `Caller`에게 전달한다.
- `Caller`는 `Subscriber`를 등록하여 back-pressure 를 조절하여 처리 가능한 만큼만 전달받는다.
  - `Callee`는 `Publisher`를 반환하고 `Caller`는 `Subscriber`를 등록한다.
  
    ➡️ `Callee`와 `Caller`는 **비동기적으로 동작**
- 구조
  - 데이터 혹은 이벤트를 제공하는 `Publisher`
    - `onSubscribe` `onNext` `onComplete` `onError` 채널을 통해 `Publisher`가 `Subscriber`에게 이벤트를 전달한다.
  - 데이터 혹은 이벤트를 제공받는 `Subscriber`
  - 데이터 흐름을 조절하는 `Subscription`
    - `Publisher`가 생성해서 `Subscriber`에 전달한다.
    - 일종의 `Subscriber` 가 쓸 수 있는 리모콘 ex) 데이터 더 줘, 데이터 그만 줘
    - `back-pressure`를 조절할 수 있는 request 함수
<br/>
<br/>
<img width="744" alt="스크린샷 2023-11-16 오후 2 26 58" src="https://github.com/thdefn/webflux/assets/80521474/97b5dc0c-4339-45d6-a418-2ef9ca2d864e">


#### Publisher
|                     Hot Publisher                      |           Cold Publisher            |
|:------------------------------------------------------:|:-----------------------------------:|
| `subscriber`가 없더라도 데이터를 생성하고 스트림에 `push`하는 `publisher` | `subscribe`가 시작되는 순간부터 데이터를 생성하고 전송 |
|              여러 `subscriber`에게 동일한 데이터 전달              |  `subscriber`에 따라 독립적인 데이터 스트림 제공   |
|                트위터 게시글 읽기, 공유 리소스 변화 등                 |          파일 읽기, 웹 API 요청 등          |

### Reactive Streams 구현 라이브러리
#### **Project Reactor**
  - `Flux`
    - 0-n개의 item 전달 ≒ `List<T>`
    - 에러가 발생하면 error 시그널 전달 후 종료
    - 모든 item 을 전달했다면 complete 시그널 전달 후 종료
    - back-pressure 지원
  - `Mono`
    - 0 or 1개의 item 전달 ≒ `Optional<T>`
      - 1개의 item 만 전달하기 때문에 next 하나만 실행하면 complete 가 보장됨
      - 혹은 전달하지 않고 complete 하면 값이 없다는 것을 의미
      - 하나의 값이 있거나 없다
    - 에러가 발생하면 error 시그널 전달 후 종료
    - 모든 item 을 전달했다면 complete 시그널 전달 후 종료
#### **RxJava**

|   `Observable`    |   `Flowable` |
|:---------------:|:----------:|
| Push 기반 | Pull 기반 |
|  Subscriber 가 처리할 수 없더라도 item 을 전달 |  Subscriber 가 request 수를 조절    |
|               Reactive manifesto 의 message driven 을 일부만 준수              |         Reactive manifesto 의 message driven 을 준수         |
|             onSubscribe 로 Disposable 전달           |         onSubscribe 시 Subscription 전달      |

  - `Flowable`
    - 0-n개의 item 전달 
    - 에러가 발생하면 error 시그널 전달 후 종료
    - 모든 item 을 전달했다면 complete 시그널 전달 후 종료
    - back-pressure 지원
    - `Flux`와 유사
  - `Observable`
    - 0-n개의 item 전달 
    - 에러가 발생하면 error 시그널 전달 후 종료
    - 모든 item 을 전달했다면 complete 시그널 전달 후 종료
    - back-pressure 지원 x
  - `Single`
    - 1개의 item 을 전달 후 바로 onComplete signal 전달
    - 1개의 item 이 없다면 onError signal 전달
    - 에러가 발생했다면 onError signal 전달
  - `Maybe`
    - 1개의 item 을 전달 후 바로 onComplete signal 전달
    - 1개의 item 이 없어도 onComplete signal 전달 가능
    - 에러가 발생했다면 onError signal 전달
    - `Mono`와 유사
  - `Completable`
    - onComplete 혹은 onError signal 전달
    - 값이 아닌 사건을 전달
<br/>
<img width="751" alt="스크린샷 2023-11-17 오후 6 32 56" src="https://github.com/thdefn/webflux/assets/80521474/c42d59ff-84cb-4960-8df0-fed4c8f6705d">

#### Mutiny
- `Multi`
    - 0-n개의 item 전달
    - 에러가 발생하면 error 시그널 전달 후 종료
    - 모든 item 을 전달했다면 complete 시그널 전달 후 종료
    - back-pressure 지원
    - `Flux`와 유사
- `Uni`
    - 0-1개의 item 전달
    - 에러가 발생하면 error 시그널 전달 후 종료
    - 모든 item 을 전달했다면 complete 시그널 전달 후 종료
    - `Mono`와 유사


### JAVA IO, NIO, AIO
#### 함수 호출 모델
|              |                동기                |                비동기                 |
|:------------:|:--------------------------------:|:----------------------------------:|
|   **Blocking**   |  Java IO  |   x  |
| **Non-blocking** | Java NIO (File IO는 Non-blocking 불가능) | Java AIO |


#### I/O 모델
|              |     동기     | 비동기 |
|:------------:|:----------:|:---:|
|   **Blocking**   |  Java IO   |  x  |
| **Non-blocking** | Java NIO, Java AIO |  x  |

#### Java IO
- 파일과 네트워크에 데이터를 읽고 쓸 수 있는 API 제공
- byte 단위로 읽고 쓸 수 있는 stream ex) `InputStream` `OutputStream`
- blocking 단위로 동작
- 문제점
  1. 동기 blocking 으로 동작
     - application 이 `read()` 를 호출하면 커널이 응답을 돌려줄 때까지 아무것도 할 수 없음
     - I/O 요청이 발생할 때마다 스레드를 새로 할당하면, 스레드를 생성 및 관리하는 비용과 컨텍스트 스위칭으로 인한 cpu 자원 소모
     </br>
     <img width="360" alt="스크린샷 2023-11-18 오후 8 35 59" src="https://github.com/thdefn/webflux/assets/80521474/b632396c-6004-492b-ac33-55eed5c2e6f3">
  3. Java IO 에서 커널 버퍼에 직접 접근 불가
     - 하드웨어에서 값을 읽어오면 disk controller 가 DMA 를 통해 커널 버퍼에 값을 복사
     - 커널 버퍼에서 jvm 버퍼로 복사하는 과정에서 cpu 자원 소모
     - jvm 버퍼에 있기 때문에 gc의 대상이 되고 이또한 cpu 자원 소모
     </br>
     <img width="545" alt="스크린샷 2023-11-18 오후 8 32 05" src="https://github.com/thdefn/webflux/assets/80521474/68d1c0b3-04f1-494a-b6f0-754c37d1ed8e">

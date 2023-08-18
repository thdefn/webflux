package project.future;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import project.common.Article;
import project.common.Image;
import project.common.User;
import project.common.repository.UserEntity;
import project.future.repository.ArticleFutureRepository;
import project.future.repository.FollowFutureRepository;
import project.future.repository.ImageFutureRepository;
import project.future.repository.UserFutureRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UserFutureService {
    private final UserFutureRepository userRepository;
    private final ArticleFutureRepository articleRepository;
    private final ImageFutureRepository imageRepository;
    private final FollowFutureRepository followRepository;

    @SneakyThrows
    public CompletableFuture<Optional<User>> getUserById(String id) {
        return userRepository.findById(id)
                .thenComposeAsync(this::getUser);
    }

    @SneakyThrows
    private CompletableFuture<Optional<User>> getUser(Optional<UserEntity> userEntityOptional) {
        if (userEntityOptional.isEmpty())
            return CompletableFuture.completedFuture(Optional.empty());

        var userEntity = userEntityOptional.get();

        CompletableFuture<Optional<Image>> imageFuture = imageRepository.findById(userEntity.getProfileImageId())
                .thenApplyAsync(imageEntityOptional ->
                        imageEntityOptional.map(imageEntity ->
                                new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()))
                );

        CompletableFuture<List<Article>> articlesFuture = articleRepository.findAllByUserId(userEntity.getId())
                .thenApplyAsync(articleEntities ->
                        articleEntities.stream().map(articleEntity ->
                                        new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent()))
                                .collect(Collectors.toList())
                );

        CompletableFuture<Long> followCountFuture = followRepository.countByUserId(userEntity.getId());

        // isDone 이 보장된 상태의 get() 은 즉시 반환함, isDone 이 아닌 상태의 get() 은 대기 후 반환
        return CompletableFuture.allOf(imageFuture, articlesFuture, followCountFuture)
                .thenAcceptAsync(v -> {
                    log.info("Three futures are completed");
                })
                .thenRunAsync(() -> {
                    log.info("Three futures are also completed");
                })
                .thenApplyAsync(v -> {
                    try {
                        var image = imageFuture.get();
                        var articles = articlesFuture.get();
                        var followCount = followCountFuture.get();

                        return CompletableFuture.completedFuture(
                                Optional.of(new User(
                                        userEntity.getId(),
                                        userEntity.getName(),
                                        userEntity.getAge(),
                                        image,
                                        articles,
                                        followCount
                                )));

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).join();
    }
}

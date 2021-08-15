DROP DATABASE `eack_xoa_client`;

CREATE DATABASE `eack_xoa_client`;

CREATE TABLE `eack_xoa_client`.`users`
(
    `id`           BIGINT       NOT NULL,
    `username`     VARCHAR(64)  NOT NULL,
    `password`     VARCHAR(64)  NOT NULL,
    `name`         VARCHAR(64)  NOT NULL,
    `email`        VARCHAR(64)  NOT NULL,
    `phone_number` VARCHAR(16)  NOT NULL,
    `bio`          VARCHAR(256) NOT NULL,
    `birth_date`   BIGINT       NOT NULL,
    `is_active`    BOOL         NOT NULL,
    `is_deleted`   BOOL         NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `eack_xoa_client`.`profiles`
(
    `id`               BIGINT   NOT NULL,
    `picture`          LONGTEXT NOT NULL,
    `last_seen`        BIGINT   NOT NULL,
    `followers`        JSON     NOT NULL,
    `followings`       JSON     NOT NULL,
    `blocked`          JSON     NOT NULL,
    `muted`            JSON     NOT NULL,
    `requests`         JSON     NOT NULL,
    `pending`          JSON     NOT NULL,
    `user_tweets`      JSON     NOT NULL,
    `retweeted_tweets` JSON     NOT NULL,
    `upvoted_tweets`   JSON     NOT NULL,
    `downvoted_tweets` JSON     NOT NULL,
    `reported_tweets`  JSON     NOT NULL,
    `saved_tweets`     JSON     NOT NULL,
    `notifications`    JSON     NOT NULL,
    `groups`           JSON     NOT NULL,
    `chats`            JSON     NOT NULL,
    `private_state`    BOOL     NOT NULL,
    `info_state`       BOOL     NOT NULL,
    `last_seen_state`  INT      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `eack_xoa_client`.`tweets`
(
    `id`          BIGINT       NOT NULL,
    `owner`       BIGINT       NOT NULL,
    `upper_tweet` BIGINT       NOT NULL,
    `picture`     LONGTEXT     NOT NULL,
    `visible`     BOOL         NOT NULL,
    `text`        VARCHAR(256) NOT NULL,
    `tweet_date`  BIGINT       NOT NULL,
    `comments`    JSON,
    `upvotes`     JSON,
    `downvotes`   JSON,
    `retweets`    JSON,
    `reports`     INT          NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`owner`) REFERENCES users (`id`),
    FOREIGN KEY (`upper_tweet`) REFERENCES tweets (`id`)
) ENGINE = InnoDB;

CREATE TABLE `eack_xoa_client`.`groups`
(
    `id`      BIGINT      NOT NULL,
    `title`   VARCHAR(64) NOT NULL,
    `members` JSON        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `eack_xoa_client`.`chats`
(
    `id`        BIGINT      NOT NULL,
    `chat_name` VARCHAR(64) NOT NULL,
    `group`     BOOL        NOT NULL,
    `users`     JSON,
    `messages`  JSON,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `eack_xoa_client`.`messages`
(
    `id`                BIGINT       NOT NULL,
    `chat_id`           BIGINT       NOT NULL,
    `owner_id`          BIGINT       NOT NULL,
    `tweet_id`          BIGINT       NOT NULL,
    `index`             INT          NOT NULL,
    `text`              VARCHAR(256) NOT NULL,
    `picture`           LONGTEXT     NOT NULL,
    `message_date_unix` BIGINT       NOT NULL,
    `seen_list`         JSON         NOT NULL,
    `sent`              BOOL         NOT NULL,
    `received`          BOOL         NOT NULL,
    `seen`              BOOL         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`chat_id`) REFERENCES chats (`id`),
    FOREIGN KEY (`owner_id`) REFERENCES users (`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES tweets (`id`)
) ENGINE = InnoDB;

CREATE TABLE `eack_xoa_client`.`notifications`
(
    `id`           BIGINT       NOT NULL,
    `owner`        BIGINT       NOT NULL,
    `request_from` BIGINT       NOT NULL,
    `text`         VARCHAR(256) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`owner`) REFERENCES users (`id`),
    FOREIGN KEY (`request_from`) REFERENCES users (`id`)
) ENGINE InnoDB;

INSERT INTO `eack_xoa_client`.`users`
(`id`, `username`, `password`, `name`, `email`, `phone_number`, `bio`, `birth_date`, `is_active`, `is_deleted`)
VALUES (-1, '', '', '', '', '', '', -1, false, true);

INSERT INTO `eack_xoa_client`.`tweets`
(`id`, `owner`, `upper_tweet`, `picture`, `visible`, `text`, `tweet_date`, `comments`, `upvotes`, `downvotes`, `retweets`, `reports`)
VALUES (-1, -1, -1, '', false, '', -1, null, null, null, null, -1);

INSERT INTO `eack_xoa_client`.`chats` (`id`, `chat_name`, `group`, `users`, `messages`) VALUES (-1, '', false, null, null);
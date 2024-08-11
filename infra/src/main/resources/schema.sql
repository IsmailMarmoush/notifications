CREATE TABLE IF NOT EXISTS `subscriptions` (
  `user_id` UUID NOT NULL PRIMARY KEY,
  `frequency` varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `selection_history` (
  `id` string UUID NOT NULL PRIMARY KEY,
  `user_id` UUID NOT NULL PRIMARY KEY,
  `frequency` varchar(255) NOT NULL,
  `startTime` BIGINT NOT NULL
);
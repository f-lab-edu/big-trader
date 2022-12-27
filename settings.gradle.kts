rootProject.name = "bigtrader"
include("app-push-api")
include("big-trader-api")
include("config")
include("config:jasypt-config")
findProject(":config:jasypt-config")?.name = "jasypt-config"
include("config:kafka-config")
findProject(":config:kafka-config")?.name = "kafka-config"
include("config:redis-config")
findProject(":config:redis-config")?.name = "redis-config"
include("stock-exchange")

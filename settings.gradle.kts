rootProject.name = "bigtrader"
include("app-push-api")
include("big-trader-api")
include("commons")
include("commons:jasypt-config")
findProject(":commons:jasypt-config")?.name = "jasypt-config"
include("commons:kafka-config")
findProject(":commons:kafka-config")?.name = "kafka-config"

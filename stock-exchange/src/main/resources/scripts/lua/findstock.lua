local stockEvent = redis.call("LPOP", KEYS[1])
return stockEvent

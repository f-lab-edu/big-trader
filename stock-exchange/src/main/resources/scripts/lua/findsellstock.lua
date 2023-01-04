local sellStock = redis.call("LPOP", KEYS[1])
return sellStock

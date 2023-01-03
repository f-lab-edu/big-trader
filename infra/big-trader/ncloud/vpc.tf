resource "ncloud_vpc" "bigtrader-vpc" {
  ipv4_cidr_block = "10.0.0.0/16"
}


resource "ncloud_subnet" "bigtradter-subnet" {
  vpc_no         = ncloud_vpc.bigtrader-vpc.id
  subnet         = "10.0.0.0/28"
  zone           = "KR-2"
  network_acl_no = ncloud_vpc.bigtrader-vpc.default_network_acl_no
  subnet_type    = "PUBLIC"
}

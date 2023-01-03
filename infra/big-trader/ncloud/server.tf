resource "ncloud_server" "big-trader-api-server" {
  name                      = "big-trader-api-server"
  subnet_no                 = ncloud_subnet.bigtradter-subnet.id
  server_image_product_code = data.ncloud_server_image.server_image.id
  server_product_code       = data.ncloud_server_product.product.id
}

data "ncloud_server_image" "server_image" {
  filter {
    name   = "os_information"
    values = ["Ubuntu Server 20.04 (64-bit)"]
  }
}

data "ncloud_server_product" "product" {
  server_image_product_code = data.ncloud_server_image.server_image.id

  filter {
    name   = "product_code"
    values = ["SSD"]
    regex  = true
  }

  filter {
    name   = "cpu_count"
    values = ["2"]
  }

  filter {
    name   = "memory_size"
    values = ["8GB"]
  }
}

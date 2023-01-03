terraform {
  required_providers {
    ncloud = {
      source  = "NaverCloudPlatform/ncloud"
      version = "2.3.7"
    }
  }
  required_version = ">=0.13"
}

provider "ncloud" {
  access_key  = var.access_key
  secret_key  = var.secret_key
  region      = var.region
  support_vpc = true
}

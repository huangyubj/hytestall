package com.hy.service;

import com.hy.entity.Catalog;

public interface OrderService {

    Catalog selectCatalog(Integer id);

    int addCatalogInfo(Catalog catalog);
}

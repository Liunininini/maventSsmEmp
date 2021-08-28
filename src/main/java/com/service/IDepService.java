package com.service;

import com.po.Dep;

import java.util.List;

public interface IDepService {
  public List<Dep> findAll();

  public boolean save(Dep dep);
}

package com.github.studeasy.logic.facades;

import com.github.studeasy.logic.factory.FactoryI;
import com.github.studeasy.logic.factory.MySQLFactory;

public abstract class AbstractFacade {

    protected FactoryI factory;

    public AbstractFacade() {
        this.factory = MySQLFactory.getInstance();
    }


}

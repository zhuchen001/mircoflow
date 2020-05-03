/**
 *
 */
package com.center.microflow.domain;

import com.center.microflow.api.IBranch;
import com.center.microflow.api.ITransactionManager;

/**
 * 具体的分支(一个分支也视为一个stage)
 *
 * @author Administrator
 *
 */
public class BranchElement extends Stage {

    private IBranch decide;


    public BranchElement(String name, VertexEnum type, IBranch decide, Object vertex) {
        this(name, type, decide, vertex, null);
    }

    public BranchElement(String name, VertexEnum type, IBranch decide, Object vertex, ITransactionManager transactionManager) {
        super(name, 0, type, vertex, transactionManager);
        this.decide = decide;
    }

    public IBranch getDecide() {
        return decide;
    }

    public void setDecide(IBranch decide) {
        this.decide = decide;
    }


}

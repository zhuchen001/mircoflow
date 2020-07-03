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

    private IBranch branch;


    public BranchElement(String name, VertexEnum type, IBranch decide, Object vertex) {
        this(name, type, decide, vertex, null);
    }

    public BranchElement(String name, VertexEnum type, IBranch decide, Object vertex, ITransactionManager transactionManager) {
        super(name, 0, type, vertex, transactionManager);
        this.branch = decide;
    }

    public IBranch getBranch() {
        return branch;
    }

    public void setBranch(IBranch branch) {
        this.branch = branch;
    }


}

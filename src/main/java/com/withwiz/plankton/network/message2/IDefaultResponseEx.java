package com.withwiz.plankton.network.message2;

/**
 * Legacy request object common interface
 */
public interface IDefaultResponseEx<TYPE_RAW_DATA> extends IDefaultResponse<TYPE_RAW_DATA> {
    /**
     * get message tail
     *
     * @return IDefaultResponseTail
     */
    IDefaultResponseTail<TYPE_RAW_DATA> getTail();
}

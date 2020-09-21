package com.withwiz.plankton.network.message2;

/**
 * Default message extended interface
 */
public interface IDefaultMessageEx<TYPE_RAW_DATA> extends IDefaultMessage<TYPE_RAW_DATA> {
    /**
     * get tail
     *
     * @return IDefaultMessageTail
     */
    IDefaultMessageTail<TYPE_RAW_DATA> getTail();
}

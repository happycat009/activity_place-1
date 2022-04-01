package com.huangjiabin.site.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangjiabin.site.sys.mapper.ReserveMapper;
import com.huangjiabin.site.sys.model.Handle;
import com.huangjiabin.site.sys.mapper.HandleMapper;
import com.huangjiabin.site.sys.model.Reserve;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.service.EmailLogService;
import com.huangjiabin.site.sys.service.HandleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-28
 */
@Service
public class HandleServiceImpl extends ServiceImpl<HandleMapper, Handle> implements HandleService {
    @Resource
    private ReserveMapper reserveMapper;
    @Resource
    private HandleMapper handleMapper;
    @Resource
    private EmailLogService emailLogService;


    public static LocalDateTime createTime=LocalDateTime.now();

    @Override
    public RespBean handle(Handle handle) {
        //判断操作是否是对预定的，是的话更新预定状态
        if(handle.getHandleTarget()==55){   //55操作申请
            Reserve reserve = new Reserve();
            reserve.setId(handle.getTargetId());
            if(handle.getHandleResult()==45){   //通过
                reserve.setReserveStatus(52);   //52预定成功
            }else if(handle.getHandleResult()==46){     //拒绝
                reserve.setReserveStatus(53);   //预定失败
            }
        }
        //新增操作信息
        handle.setHandleTime(createTime);
        handle.setIsEmailSuccess(0);  //
        int insert = handleMapper.insert(handle);
        if(insert>0){
            return RespBean.success("操作成功",handle);
        }
        return RespBean.error("操作失败");
    }

}

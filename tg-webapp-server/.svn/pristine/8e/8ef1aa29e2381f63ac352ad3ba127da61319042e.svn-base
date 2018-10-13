package cn.plou.web.system.permission.verificationCode.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.system.permission.verificationCode.entity.VerificationCodeInfo;
import cn.plou.web.system.permission.verificationCode.service.VerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletRequest;
import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/verificationCode")
@EnableSwagger2
@Api(description = "验证码")
public class VerificationCodeController {

    @ApiOperation(value = "获取验证码")
    @PostMapping("/getVerificationCode")
    public Root getVerificationCode(ServletRequest request) {
        Root root = new Root();
        List<String> list = VerificationCodeService.getVerificationCode(request);
        VerificationCodeInfo verificationCodeInfo = new VerificationCodeInfo();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        verificationCodeInfo.setUuid(uuid);
        String path = list.get(1);
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        String image = encoder.encodeToString(data);
        verificationCodeInfo.setImage(image);
        RedisUtil redisUtil = RedisUtil.getSingleton();
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            jedis.set(uuid, list.get(0));
        } finally {
            RedisUtil.returnSource(jedis);
        }
        root.setData(verificationCodeInfo);
        File file = new File(path);
        file.delete();
        return root;
    }

//    @ApiOperation(value = "验证验证码")
//    @GetMapping("/verification")
//    public Boolean verification(@RequestParam String verificationCode,@RequestParam String uuid) {
//        RedisUtil redisUtil = new RedisUtil();
//        Jedis jedis = redisUtil.getJedis();
//        String code = jedis.get(uuid);
//        jedis.del(uuid);
//        return code.equals(verificationCode);
//    }
}

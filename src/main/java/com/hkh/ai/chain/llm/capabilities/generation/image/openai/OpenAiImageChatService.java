package com.hkh.ai.chain.llm.capabilities.generation.image.openai;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.OpenAiServiceProxy;
import com.hkh.ai.chain.llm.capabilities.generation.image.ImageChatService;
import com.hkh.ai.config.SysConfig;
import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OpenAiImageChatService implements ImageChatService {

    @Autowired
    private OpenAiServiceProxy openAiServiceProxy;

    @Autowired
    private SysConfig sysConfig;
    @Override
    public List<String> createImage(String prompt) {
        OpenAiService service = openAiServiceProxy.service();
        CreateImageRequest createImageRequest = CreateImageRequest
                .builder()
                .model("dall-e-3")
                .responseFormat("b64_json")
                .prompt(prompt)
                .n(1)
                .quality("standard")
                .size("1024x1024")
                .style("vivid")
                .build();
        ImageResult imageResult = service.createImage(createImageRequest);
        List<String> result = new ArrayList<>();
        for (Image image : imageResult.getData()){
            result.add("data:image/jpg;base64," +image.getB64Json());
        }
        return result;
    }

    @Override
    public List<String> editImage(String prompt, List<String> httpUrls) {
        OpenAiService service = openAiServiceProxy.service();
        CreateImageEditRequest createImageEditRequest = CreateImageEditRequest
                .builder()
                .model("dall-e-2")  // only dall-e-2 is supported at this time
                .responseFormat("b64_json")
                .prompt(prompt)
                .n(1)
                .size("1024x1024")
                .build();
        String fileName = System.currentTimeMillis() + ".png";
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(now);
        File destFile = new File(sysConfig.getUploadPath() + File.separator + "image" + File.separator + dateStr + File.separator + fileName);
        File file = HttpUtil.downloadFileFromUrl(httpUrls.get(0), destFile);

        ImageResult imageResult = service.createImageEdit(createImageEditRequest, file, null);
        List<String> result = new ArrayList<>();
        for (Image image : imageResult.getData()){
            result.add("data:image/jpg;base64," + image.getB64Json());
        }
        return result;
    }
}

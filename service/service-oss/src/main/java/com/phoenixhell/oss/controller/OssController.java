package com.phoenixhell.oss.controller;

import com.phoenixhell.oss.service.OssService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author phoenixhell
 * @since 2021/3/5 0005-下午 2:11
 */
@RestController
//路徑多了個空格就是跨域
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    public CommonResult uploadOss(MultipartFile file){
        String fileUrl = ossService.uploadFileToOss(file);
        return CommonResult.ok().emptyData().data("fileUrl",fileUrl);
    }
}
/*
<template>
  <div id="app">
    <div class="example">
      <cropper ref="cropper" class="example-cropper" :src="image" stencil-component="circle-stencil" />
      <div class="button-wrapper">
        <span class="button" @click="$refs.file.click()">
          <input
            type="file"
            ref="file"
            @change="uploadImage($event)"
            accept="image/*"
          />
          Upload image
        </span>
        <span class="button" @click="cropImage">Crop image</span>
      </div>
    </div>
  </div>
</template>

<script>
import { Cropper } from "vue-advanced-cropper";
import "vue-advanced-cropper/dist/style.css";
import uploadavatar from '@/api/oss/uploadavatar'

export default {
  name: "App",
  data() {
    return {
      image:
        "https://phoenixhell.oss-cn-shanghai.aliyuncs.com/2021/03/05/46ae2a8cacb8410385a1e018279a3cae.jpg",
    };
  },
  methods: {
    cropImage() {
			const { canvas } = this.$refs.cropper.getResult();
      if (canvas) {
          const form = new FormData();
          canvas.toBlob(blob => {
          let file = new File([blob], "temp.jpg")
            form.append('file', file);
            // You can use axios, superagent and other libraries instead here
            uploadavatar.uploadavatar(form)
            .then(response=>{
              console.log(response)
            }).catch(error=>{
              console.log(error)
            })
            // Perhaps you should add the setting appropriate file format here
          }, 'image/jpeg');
        }
    },
    uploadImage(event) {
      // Reference to the DOM input element
      var input = event.target;
      // Ensure that you have a file before attempting to read it
      if (input.files && input.files[0]) {
        // create a new FileReader to read this image and convert to base64 format
        var reader = new FileReader();
        // Define a callback function to run, when FileReader finishes its job
        reader.onload = (e) => {
          // Note: arrow function used here, so that "this.imageData" refers to the imageData of Vue component
          // Read image as base64 and set to imageData
          this.image = e.target.result;
        };
        // Start the reader job - read file as a data url (base64 format)
        reader.readAsDataURL(input.files[0]);
      }
    },
  },
  components: {
    Cropper,
  },
};
</script>

<style>
.example-cropper {
  border: solid 1px #EEE;
  min-height: 300px;
  max-height: 400px;
  width: 100%;
}

.button-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 17px;
}

.button {
  color: white;
  font-size: 16px;
  padding: 10px 20px;
  background: #3fb37f;
  cursor: pointer;
  transition: background 0.5s;
  font-family: Open Sans, Arial;
  margin: 0 10px;
}

.button:hover {
  background: #38d890;
}

.button input {
  display: none;
}
</style>
import request from "@/utils/request";

export default {
  uploadavatar(form) {
    return request({
      url: `/eduoss/fileoss/upload`,
      method: "POST",
      data: form
    });
  }
};

 */

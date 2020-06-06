

### 一、环境配置：

- 1.下载FFmpeg源码：git clone https://github.com/FFmpeg/FFmpeg.git

- 2.配置：./configure --prefix=/usr/local/ffmpeg  --enable-gpl  --enable-nonfree  --enable-libfdk-aac --enable-libx264   --enable-libx265 --enable-libavutil   --enable-filter=delogo --enable-debug  --disable-optimizations    --enable-libspeex  --enable-videotoolbox --enable-shared --enable-pthreads --enable-version3  --enable-hardcoded-tables    --cc=clang  --host-cflags=  --host-ldflags= 
  这时候会有错误，一般是某些库没有；
  ERROR: libfdk_aac not found   
     解决：https://www.jianshu.com/p/b6ad3b706321   注意libx264 安装的时候要去掉lib
     brew install x264     fdk-aac
     还有很多需要安装的，按照这个格式去安装就行

- 3.给予系统权限：1.sudo su  2. 执行make && make install 

- 4.编译完成后会在/usr/local/ffmpeg 生成ffmpeg文件，点击进去查看，bin里面是否有ffplay，这个有可能编译不出来，原因是没有安装sdl2 ，去brew install sdl2

- 5.配置bin的路径到bash_profile    后面就可以直接使用ffmepg ffplay等命令了。

  

### 二、FFmpeg的处理流程

![image-20191127214753130](/Users/wangchao/Library/Application Support/typora-user-images/image-20191127214753130.png)

#### 3.1 处理步骤

1. 输入文件：比如mp4，flv等具有封装格式的文件，它们像盒子一样，里面装有音频，视频字幕等。
2. demuxer：解复用，相当于打开盒子操作。
3. 编码后数据帧：压缩过的音频、视频。
4. decoder：解压缩数据
5. 解码后数据帧：原始数据，但是与设备采集的数据还是有一点的差异
6. 操作数据，比如将720p转换为480p
7. encoder：编码
8. 编码数据包：编码后的数据包
9. muxer：复用，重新封装播放器可以识别的文件。
10. 播放出的视频文件。

####3.2 视频编码

音视频设备采集的**原始数据特别大**，每一秒的视频可能有200M，这样的话，一部电影可能就有1000多G，这是无法想象的，我们采用编码，将数据打包压缩后进行传输。 类似于我们在搬家的过程中要将一些东西都整理起来放到纸箱盒子里面去，一次全部运输过去，避免多次运输，到家后再打开纸箱，取出东西。

##### 3.2.1 视频编码的原理 ：

#####   编码是怎样将很大的数据变小的

- 空间冗余：图像相邻像素之间有很强的关联性 
- 时间冗余：视频序列的相邻图像之间内容相似
- 编码冗余：不同像素出现的概率不同
- 视觉冗余：人的视觉对某些细节不敏感
- 知识冗余：规律性的结构可由先前知识和背景知识得到

##### 3.2.1压缩编码的方法

主要采用运动估计和运动补偿；运动估计和运动补偿是消除图像序列时间方向相关性的有效手段。

例如对于像新闻联播这种背景静止，画面主体运动较小的数字视频，每一幅画面之间的区别很小，画面之间的相关性很大。对于这种情况我们没有必要对每一帧图像单独进行编码，而是可以只对相邻视频帧中变化的部分进行编码，从而进一步减小数据量，这方面的工作是由运动估计和运动补偿来实现的。

#####3.2.2压缩数据类型

编码器将输入的每一视频帧分为：I帧，B帧，P帧；

- I帧：只使用本帧的数据内进行编码，在编码过程中不需要进行运动估计和运动补偿
- P帧：使用前面的I帧或者P帧进行运动补偿，实际上是编码和前一帧的差值
- B帧：参考前面和后面的帧进行预测

##### 3.2.2 编码器

H264 H265

#### 3.3音频编码

压缩编码的方法

- 频谱掩蔽
- 时域掩蔽

参考文章：

https://blog.csdn.net/leixiaohua1020/article/details/18893769  

https://juejin.im/post/5d29d884f265da1b971aa220





三、FFmpeg基本信息查询命令

![image-20191127220205607](/Users/wangchao/Library/Application Support/typora-user-images/image-20191127220205607.png)

四、录制视频和音频。

  1.录制命令：ffmpeg -f avfoundation -i 1 -r 30 out.yuv 

​     -f：指定使用avfoundation 采集数据

​     -i 指定从哪里采集，它是一个索引文件号 0是camera 1是capture screen

​     -r 指定帧率

2.播放视频：ffplay -video_size 3360x2100 -pix_fmt uyvy422 out.yuv 

   -video_size  视频像素大小，否则会报错：Picture size 0x0 is invalid out.yuv: Invalid argument

   -pix_fmt 指定录制的视频格式 ，ffplay默认是420p，不一致会导致播放花屏，也就是YUV值不一致

3.查看设备支持的录制参数：ffmpeg -f avfoundation -list_devices true -i "" 

​     ![image-20191127225339230](/Users/wangchao/Library/Application Support/typora-user-images/image-20191127225339230.png)

4 音频的录制：ffmpeg -f avfoundation -i :0 -r 30 out.wav

​      播放音频：ffplay out.wav   　由于这里指定了播放的格式为wav 所以直接播放即可，不需要像那样去加参数

5 音频和视频同时录制：

录制：ffmpeg -f avfoundation -i 1:1 -r 30 out.mp4  

播放 ffplay out.mp4

6.录制音频和视频，但是视频是使用camera

录制：ffmpeg -f avfoundation -video_size 640x480 -framerate 30 -i 0:0 -r 30 out.mp4

播放：ffplay out.mp4

注意：使用camera的时候有可能会报错，就是不支持录制的帧率，这时候就要手动的去指定，-framerate。错误提示：Selected framerate (29.970030) is not supported by the device



五、音视频处理

  1.格式转换： ffmpeg -i test.mp4 -vcodec copy -acodec copy test.mov

  2.视频抽取：ffmpeg -i test.mov -an -vcodec copy out.h264

​     音频抽取：ffmpeg -i test.mov -vn -acodec copy out.aac

3 抽取YUV数据：ffmpeg -i test.mp4 -an -c:v rawvideo -pix_fmt yuv420p out.yuv

   播放： ffplay -video_size 464x960 out.yuv 

   抽取PCM数据：ffmpeg -i test.mp4 -vn -ar 44100 -ac 2 -f s16le test.pcm

   播放：ffplay -ar 44100 -ac 2 -f s16le test.pcm     播放的时候需要指定采样率 声道数 存储格式等参数

  4.裁剪尺寸大小： ffmpeg -i test.mp4 -vf crop=in_w-400:in_h-400 -c:v libx264 -c:a copy v.mp4

   添加水印： ffmpeg -i test.mp4 -vf "movie=medal.png [watermark]; [in][watermark] overlay=10:10 [out]" water.mp4

5.视频时间裁剪：ffmpeg -i test.mp4 -c:v libx264 -c:a copy -ss 00:00:02 -t 7 1.ts      注意这里需要加上-c:v libx264 -c:a copy 否则裁剪出来的视频是模糊的

  视频的合并：ffmpeg -f concat -i merger.txt merge.mp4

6.视频图片互转

   视频转图片：ffmpeg -i test.mp4 -r 1 -f image2 image-%3d.jpeg   r是每秒钟提取的帧数

   图片转视频 ffmpeg -i foo-%4d.jpeg -r 29.47 imager.mp4    要想和原视频时间一样，需要设置一样的帧率



六 FFmpeg 开发

 1.clang  

​     编译某个.c生成库 clang -g -c add.c   结果为add.o

​    生成静态库 libtool -static -o libmylib.a add.o   生成　libmylib　库文件

​    链接：clang -g -o testadd testadd.c -I . -L . -lmylib    testadd就是可执行文件

​     执行： ./testadd

2. c 调试

   ![image-20191129223916307](/Users/wangchao/Library/Application Support/typora-user-images/image-20191129223916307.png)

### FFmpeg对文件的操作

- avio_open_dir
- avio_read_dir
- avio_free_directory_entry
- avio_close_dir

### 三、多媒体文件的基本概念

- 多媒体文件其实是个容器，容器里面有很多了流 stream/track。

- 每种流是有不同的编码器编码的，从流中读出的数据称为包---》〉在一个包中含有一个或多个帧

- 几个重要结构体

  ​    AVFormatContext 媒体格式上下文

  ​    AVStream  数据流

  ​    AVPacket 数据包

- ffmpeg操作数据的基本步骤

     解复用-》获取流-> 读取数据包-〉释放资源


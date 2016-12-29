
    if (flvjs.isSupported()) {
        var videoElement = document.getElementById('videoElement');
        var flvPlayer = flvjs.createPlayer({
     //       type: 'flv',
      //      url: 'flv/video.flv'
			type: 'mp4',
            url: 'http://tb-video.bdstatic.com/tieba-movideo/2847221_951a3162427c836e08d21b0633966833.mp4'
        });
        flvPlayer.attachMediaElement(videoElement);
        function flv_load () {
            flvPlayer.load();
        }
        function flv_start () {
            flvPlayer.play();
        }
        function flv_pause() {
            flvPlayer.pause();
        }
        function flv_destroy() {
            flvPlayer.pause();
            flvPlayer.unload();
            flvPlayer.detachMediaElement();
            flvPlayer.destroy();
            flvPlayer = null;
        }
        function flv_seekto() {
            var input = document.getElementsByName('seekpoint')[0];
            flvPlayer.currentTime = parseFloat(input.value);
        }
	}
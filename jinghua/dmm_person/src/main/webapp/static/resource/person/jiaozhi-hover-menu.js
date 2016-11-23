$(function(){ 
	$(".jiaozhi-hover-menu li:eq(0)").addClass("jiaozhi-hover-menu-selected");
    $(".jiaozhi-hover-menu li").click(function() { 
        var el = $(this).attr('class'); 
         $('html, body').animate({ 
             scrollTop: $("#"+el).offset().top 
         }, 300); 
        //切换菜单样式 
    	$(this).parent().find("li").removeClass("jiaozhi-hover-menu-selected"); 
        $(this).addClass("jiaozhi-hover-menu-selected");
     }); 
    
    function set_cur(n){ 
        if($(".jiaozhi-hover-menu li").hasClass("jiaozhi-hover-menu-selected")){ 
            $(".jiaozhi-hover-menu li").removeClass("jiaozhi-hover-menu-selected"); 
        } 
        $(".jiaozhi-hover-menu li"+n).addClass("jiaozhi-hover-menu-selected"); 
    } ;
    var li_menu_01 = $("#li_menu_01").offset().top; //距页顶偏移量 
    var li_menu_02 = $("#li_menu_02").offset().top; 
    var li_menu_03 = $("#li_menu_03").offset().top; 
    var li_menu_04 = $("#li_menu_04").offset().top; 
    var li_menu_05 = $("#li_menu_05").offset().top; 
    var li_menu_06 = $("#li_menu_06").offset().top; 
    var li_menu_07 = $("#li_menu_07").offset().top;
    var scrollFunc=function(){//滚动页面 
        var scroH = $(this).scrollTop(); //滚动条位置 
        if(scroH>=li_menu_07){ 
            set_cur(".li_menu_07");//设置状态 
        }else if(scroH>=li_menu_06){ 
            set_cur(".li_menu_06"); 
        }else if(scroH>=li_menu_05){ 
            set_cur(".li_menu_05"); 
        }else if(scroH>=li_menu_04){ 
            set_cur(".li_menu_04"); 
        }else if(scroH>=li_menu_03){ 
            set_cur(".li_menu_03"); 
        }else if(scroH>=li_menu_02){ 
            set_cur(".li_menu_02"); 
        }else if(scroH>=li_menu_01){ 
            set_cur(".li_menu_01"); 
        } 
    }; 
    if(document.addEventListener){
         document.addEventListener('DOMMouseScroll',scrollFunc,false);
    }//W3C
    window.onmousewheel=document.onmousewheel=scrollFunc;//IE/Opera/Chrome/Safari
}); 

// ---------------------- 图片新闻和第一页切换新闻 ---------------------- //
$(document).ready(function() {
	
  var mySwiper2 = new Swiper('#banner',{
	  autoplayDisableOnInteraction:false,
	  autoplay:3000,
	  loop:true,
	  pagination :'.paginations',
  });

	$(".paginations span").click(function(e){
		e.preventDefault();
		mySwiper2.slideTo( $(this).index()+1 );
		mySwiper2.autoplay = true;
	});
	var tabsSwiper = new Swiper('#tabs-container',{
    speed:500,
    onSlideChangeStart: function(){
      $(".tabs .active").removeClass('active')
      $(".tabs a").eq(tabsSwiper.activeIndex).addClass('active')  
    }
  })
  $(".tabs a").mouseover(function(e){
    e.preventDefault()
	$(".tabs .active").removeClass('active')
    $(this).addClass('active')
    tabsSwiper.slideTo( $(this).index() )
  })
});
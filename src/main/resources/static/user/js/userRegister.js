$(document).ready(function () {

  $('.sendEmail').click(function () {
    let email = $('#email').val()

    $.ajax({
      type: "GET",//方法类型

      dataType: "json",//预期服务器返回的数据类型

      //设置跨域
      xhrFields:{
        withCredentials: true
      },

      url: 'http://localhost:8888/user/sendEmail?targetEmail=' + email,

    });

  })

})

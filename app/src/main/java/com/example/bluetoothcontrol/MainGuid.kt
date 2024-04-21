package com.example.bluetoothcontrol;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainGuid: AppCompatActivity() {
    private var content:ScrollView?=null
    private var contentTV:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guid_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN and WindowManager.LayoutParams.FLAG_FULLSCREEN)

        content = findViewById(R.id.contentGuid)
        contentTV = findViewById(R.id.contentTV)

        contentTV?.text =
            " Ứng dụng này là sản phấm kết hợp với Robot Support Transportation, dùng để kết nối, khởi động " +
            "các chức năng điều khiển từ xa.\n" +
            " Trên giao diện của ứng dụng có các phím chức năng như sau:\n" +
            "   1. Hướng dẫn: Chuyển hướng đến trang hướng dẫn kết nối và sử dụng Robot, là trang hiện tại" +
                    " mà bạn đang xem, chứa nội dung về chức năng của ứng dụng và cách sử dụng \n" +
            "   2. Kết nối: Chức năng kết nối với robot thông qua công nghệ không dây Bluetooth. Bạn phải đảm bảo" +
                    " rằng thiết bị có hỗ trợ công nghệ này và cấp quyền cho phép tìm và kết nối với thiết bị khác." +
                    " Khi bạn chọn chức năng này, sẽ được chuyển hướng đến trang kết nối bluetooth, bạn có thể quét thiết bị mới" +
                    " hoặc kết nối lại với thiết bị đã từng kết nối. Bạn chỉ cần chọn tên thiết bị cần kết nối và chờ trong giây lát" +
                    " để ứng dụng xử lý kết nối. Sau khi kết nối thành công, thì nút trạng thái ở góc trên bên phải sẽ thay đổi từ màu" +
                    " ĐỎ sang XANH và hiển thị tên thiết bị kết nối ở bên cạnh và đồng thời tên của nút sẽ đổi thành Ngắt Kết Nối. " +
                    " Để ngắt kết nối chỉ cần ckick vào nút và chờ trong giây lát, nếu ngắt kết nối thành công thì nút trạng thái sẽ chuyển" +
                    " lại sang màu ĐỎ và không còn hiển thị tên thiết bị kết nối. \n" +
            "   3. Bất đầu: Cấu hình Robot hoạt động ở chế độ nhận dạng và bám theo người, Robot sẽ phát một đoạn âm thanh để hướng dẫn bạn" +
                    " sử dụng, hãy làm theo nó và hãy đảm bảo rằng bạn đã kết nối thành công với Robot. Khi đã cấu hình thành công thì Robot sẽ thực thi chương trình" +
                    " để thực hiện chức năng đi theo bạn, lưu ý vì đây là sẵn phẩm chưa được hoàn thiện về chức năng nên khả năng nhạn dạng còn nhiều sai sót, " +
                    " trong quá trình di chuyển nên kiểm tra tình trạng của Robot. Tên nút sẽ thay đổi thành Kết Thúc khi cấu hình thành công, vì vậy bạn có thể dừng chương trình" +
                    " bất cứ lúc nào,chỉ cần click vào nút này.\n "+
            "   4. Điều khiển: Ngoài chức năng tự hành, bạn có thể lựa chọn điều khiển thử công bằng trình điều khiển khi click vào nút này. Sẽ chuyển hướng bạn đến trang" +
                    " điều khiển, trên giao diện có các phím điều hướng, bạn có thể tùy chỉnh theo ý của mình. Đảm bảo rằng vẫn còn kết nối Bluetooth\n" +
            "   5. Thông tin ứng dụng: Sẽ xuất hiện một số thông tin về ứng dụng. \n" +
            "   6. Khởi động lại: Phím ở gốc trên bên trái của giao diện ứng dụng, phím này có chức năng khởi động lại chương trình trên robot " +
                    ". Vì vậy bạn phải thực hiện lạ các kết nối và cài đặt ban đầu.\n" +

            "\n\nĐây là sản phẩm có thể chưa được hoàn thiện về chức năng nên trong quá trình sử dụng sẽ gặp một số lỗi. Mong bạn có thể thông cảm và phản hồi lại cho chúng tôi."


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val ctrIt = Intent(this, MainControl::class.java)
        startActivity(ctrIt)
        finish()
    }
}
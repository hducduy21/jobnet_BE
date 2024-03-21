![image](https://github.com/hducduy21/jobnet_BE/assets/113038909/b04abf19-6e65-48d5-8335-9b0ecf13a180)# Ứng Dụng Công Nghệ trong Việc Phát Triển UX/UI trong hệ thống tìm việc làm
## Mục Lục

1. [Cách Chạy Backend](#how_to_run)
    1. [Clone dự án về máy](#clone_project)
    2. [Di chuyển terminal đến thư mục vừa clone về, tiến hành chèn dữ liệu mẫu vào Database](#insrt_data)
    3. [Khởi chạy Docker Container](#start_container)
    4. [Cấu hình và chạy dự án](#config_to_run_project)
    5. [Hoàn Thành](#done)
       

## Cách Chạy Back-end

1. **Clone dự án về máy:**
  ```bash
    $ git clone https://github.com/hducduy21/jobnet_BE.git
  ```
2. **Di chuyển terminal đến thư mục vừa clone về, tiến hành chèn dữ liệu mẫu vào Database:**
   Trong dự án vừa clone có thư mục <b>data</b>, lần lượt chạy hai câu lệnh bên dưới:
  ```bash
    $ docker cp mongo mongo:/dump
    $ docker exec mongo mongorestore -u root -p password dump
  ```
3. **Khởi chạy Docker Container:**
  - Tiến hành dừng và xoá các Docker Container:
  ```bash
    $ docker compose down
  ```
  - Sau đó tạo mới và khởi chạy các Docker Container:
  ```bash
    $ docker compose up -d
  ```

4. **Cấu hình và chạy dự án**
  Mở thư mục <b>jobnet-serve</b> bằng IntelliJ IDE:
  Cấu hình:
     ![image](https://github.com/hducduy21/jobnet_BE/assets/113038909/8d66c9dc-771a-43d7-bdb1-51aec4e77bbf)

   
  - Thông tin cấu hình:
    + CONFIG_GIT_PASSWORD: {cipher}053eb0db02cb458d65ff299f7fbfb4d72e9eed960d1b617a9aa96c99f562e28c160d15fd9bcc517cd949a82f5c496019fa82188cba14bc846363b70b577270f6fe13a75c4b6a9bbf0c812e29d8aa56d0923e749a573e36630e6454a20755bcf2de3888a313371b22087d5cce29687d22
    + CONFIG_GIT_USERNAME: duycoiz1204@gmail.com
    + ENCRYPT_KEY: V6yIDf9SkvjmocEu5dCcCFqs7wznTOyq
      
  - Thứ tự chạy các Service:
  ```bash
    $ ConfigApplication -> APIGatewayApplication -> EurekaApplication -> và lần lượt các Service còn lại
  ```
5. Các Service sau khi chạy xong thì có thể sử dụng bình thường

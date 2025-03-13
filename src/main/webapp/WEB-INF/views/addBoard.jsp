<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container mt-5">
  <h2 class="mb-4">📝 새 글 등록</h2>

  <table class="table table-bordered">
    <tbody>
    <tr>
      <th class="table-dark text-center" width="20%">제목</th>
      <td><input type="text" class="form-control" placeholder="제목을 입력하세요"></td>
    </tr>
    <tr>
      <th class="table-dark text-center">작성자</th>
      <input type="hidden" id="userId" value="${sessionScope.userId}"/>
      <td><input type="text" class="form-control" value="${sessionScope.userName}" readonly></td>
    </tr>
    <tr>
      <th class="table-dark text-center">내용</th>
      <td><textarea class="form-control" id="boardContent" rows="5" placeholder="내용을 입력하세요"></textarea></td>
    </tr>
    </tbody>
  </table>

  <!-- 버튼 그룹 -->
  <div class="d-flex justify-content-center mt-3">
    <button type="button" class="btn btn-dark me-2" onclick="addBoard();">등록</button>
  </div>
</div>

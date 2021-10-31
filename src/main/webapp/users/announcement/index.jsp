<%@page import="announcement_dao.UserAnnouncementShowDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_user.jsp" flush="true" />

		<div class="row py-3">
			<div class="col-4"><h3>運営からのお知らせ</h3></div>
		</div>
		
		<% announcement_dao.UserAnnouncementIndexDAO uaid = new announcement_dao.UserAnnouncementIndexDAO();  %>
		<% int uid = ((beans.UserAccountBeans) (request.getSession(false).getAttribute("user"))).getUid(); %>
		<% java.util.List<beans.UserAnnouncementBeans> uab_list = uaid.findAllAnnouncement(uid); %>
		
		<table class="table">
			<tr class="bg-light">
				<th>タイトル</th>
			</tr>
		
			<%for(int i = uab_list.size() - 1; i >= 0; i--){%>
		        <% beans.UserAnnouncementBeans uab = uab_list.get(i);%>
		        <tr>
		            <td>
		            	<% if (uab.getIsFocused()) {%>
		            		<span class="text-primary"><i class="fas fa-flag"></i></span>
		            	<% } else if (!uab.getIsOpened() && !uab.getIsFocused()) { %>
		            		<span class="text-danger">NEW!</span>
		            	<% } %>
		            	<a href="/LearnSqlServlet/users/announcement/show.jsp?aid=<%= uab.getAid() %>"><%= uab.getTitle() %></a>
		            </td>
		        </tr>
		    <% } %>
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>
package board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Ch11BoardCtrl
 */
@WebServlet("/BoardCtrl")
public class BoardCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		mainCtrl(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		mainCtrl(request, response);
	}
	
	protected void mainCtrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");	//한글이 깨지는걸 방지
		response.setContentType("text/html; charset=utf-8");
		
		String cmd = request.getParameter("cmd");
		String viewPage = null;
		
		BoardSvc svc = new BoardSvc();
		
		if("sltMul".equals(cmd))		//전체조회
		{
			try {
				viewPage = svc.ArtiList(request, response);	
			} catch(Throwable e) {
				throw new ServletException(e);
			}
		}
		else if("sltOne".equals(cmd))		//단건조회
		{
			try {
				viewPage = svc.artiListOne(request, response);
			} catch(Throwable e) {
				throw new ServletException(e);
			}
		}
		else if("update_Be".equals(cmd))	//수정준비
		{
			try {
				viewPage = svc.updateBe(request, response);
			} catch(Throwable e) {
				throw new ServletException(e);
			}
		}
		else if("update".equals(cmd))		//수정처리
		{
			try {
				viewPage = svc.artiUpdate(request, response);
			} catch(Throwable e) {
				throw new ServletException(e);
			}
		}
		else if("insert".equals(cmd))		//입력처리
		{
			try {
				viewPage = svc.artiInsert(request, response);
			} catch(Throwable e) {
				throw new ServletException(e);
			}
		}
		else if("insert_Be".equals(cmd))	//입력준비
		{
			try {
				viewPage = svc.artiInsertBe(request, response);
			} catch(Throwable e) {
				throw new ServletException(e);
			}
		}
		else if("delete".equals(cmd))		//삭제처리
		{
			try {
				viewPage = svc.delete(request, response);
			} catch(Throwable e) {
				throw new ServletException(e);
			}
		}
		else if("delete_Be".equals(cmd))	//삭제준비
		{
			try {
				viewPage = svc.deleteBe(request, response);
			} catch(Throwable e) {
				throw new ServletException(e);
			}
		}
		
		if(viewPage != null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
	}

}

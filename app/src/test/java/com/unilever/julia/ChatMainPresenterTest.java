package com.unilever.julia;

import com.unilever.julia.data.model.Conversations;
import com.unilever.julia.components.model.InnovationCardVerticalModel;
import com.unilever.julia.ui.chat.ChatMainInteractor;
import com.unilever.julia.ui.chat.ChatMainPresenter;
import com.unilever.julia.ui.chat.ChatMainPresenterImpl;
import com.unilever.julia.ui.chat.ChatMainView;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class ChatMainPresenterTest {

    @Captor
    private ArgumentCaptor<InnovationCardVerticalModel> customerArgument1;

    @Captor
    private ArgumentCaptor<String> customerArgument2;

    @Mock
    private ChatMainView mView;

    @Mock
    private ChatMainInteractor mInteractor;

    private ChatMainPresenter<ChatMainView, ChatMainInteractor> mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new ChatMainPresenterImpl<>(mView, mInteractor);
    }

    @Test
    public void addConversationInView_test() {
        String json = "{\n" +
                "    \"text\": \"dove\",\n" +
                "    \"sessionCode\": \"cedaf76e-7513-4702-99be-596341abb591\",\n" +
                "    \"answers\": [\n" +
                "        {\n" +
                "            \"title\": \"CARD_INOVACAO\",\n" +
                "            \"text\": \"Confira os projects de inovação de dove: \",\n" +
                "            \"technicalText\": \"[{\\\"icon\\\":\\\"e90d\\\",\\\"color\\\":\\\"9CA7AC\\\",\\\"text\\\":\\\"PERSONAL CARE\\\",\\\"projects\\\":\\\"1\\\",\\\"icon2\\\":\\\"e91a\\\",\\\"entidades\\\":\\\"02_INOVACAO_LISTA\\\"}]\",\n" +
                "            \"audio\": null,\n" +
                "            \"options\": null\n" +
                "        }\n" +
                "    ],\n" +
                "    \"context\": {\n" +
                "        \"previous_node\": \"8\",\n" +
                "        \"intents\": [\n" +
                "            {\n" +
                "                \"name\": \"01_INOVACAO\",\n" +
                "                \"confidence\": 0.9329825\n" +
                "            }\n" +
                "        ],\n" +
                "        \"next_node\": null,\n" +
                "        \"entities\": [\n" +
                "            {\n" +
                "                \"name\": \"dove\",\n" +
                "                \"value\": \"0.997055352\",\n" +
                "                \"position\": null,\n" +
                "                \"type\": \"Marcas\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        Conversations conversations = new Gson().fromJson(json, Conversations.class);
        //mPresenter.addConversationInView(conversations);
        //Mockito.verify(mView).addJuliaInovacaoCard(customerArgument1.capture());//, customerArgument2.capture());

        InnovationCardVerticalModel model = customerArgument1.getValue();
        Assert.assertEquals(1, model.getItems().size());
    }
}

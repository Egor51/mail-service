package com.dev.mailservice.util;

public class EmailTemplateUtil {

    public static String getPropertySaleTemplate(String recipientName) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Премиальное предложение недвижимости</title>
                </head>
                <body style="font-family: 'Helvetica Neue', Arial, sans-serif; line-height: 1.6; color: #2D3748; margin: 0; padding: 0; background-color: #F7FAFC;">
                    <!-- Main Container -->
                    <div style="max-width: 600px; margin: 0 auto; background-color: #FFFFFF; border-radius: 16px; overflow: hidden; margin-top: 20px; margin-bottom: 20px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                        <!-- Header Banner -->
                        <div style="background: linear-gradient(135deg, #4F46E5 0%%, #7C3AED 100%%); padding: 40px 20px; text-align: center;">
                            <h1 style="color: #FFFFFF; margin: 0; font-size: 28px; font-weight: 700;">Премиальная Недвижимость</h1>
                            <p style="color: #E2E8F0; margin-top: 10px; font-size: 16px;">Эксклюзивное предложение для Вас</p>
                        </div>

                        <!-- Main Content -->
                        <div style="padding: 40px 30px;">
                            <!-- Greeting -->
                            <div style="margin-bottom: 30px;">
                                <h2 style="color: #1A202C; font-size: 20px; margin: 0;">Здравствуйте, %s! 👋</h2>
                                <p style="color: #4A5568; margin-top: 15px; font-size: 16px; line-height: 1.8;">
                                    Мы - эксперты в сфере элитной недвижимости, и у нас есть уникальное предложение, 
                                    которое может быть интересно именно Вам.
                                </p>
                            </div>

                            <!-- Market Insights -->
                            <div style="background-color: #F8FAFC; border-radius: 12px; padding: 25px; margin-bottom: 30px;">
                                <h3 style="color: #2D3748; font-size: 18px; margin: 0 0 20px 0;">📈 Рыночная аналитика</h3>
                                <ul style="list-style: none; padding: 0; margin: 0;">
                                    <li style="display: flex; align-items: center; margin-bottom: 15px; color: #4A5568;">
                                        <span style="background-color: #4F46E5; width: 8px; height: 8px; border-radius: 50%%; display: inline-block; margin-right: 15px;"></span>
                                        Рост цен на недвижимость в вашем районе: +15%%
                                    </li>
                                    <li style="display: flex; align-items: center; margin-bottom: 15px; color: #4A5568;">
                                        <span style="background-color: #4F46E5; width: 8px; height: 8px; border-radius: 50%%; display: inline-block; margin-right: 15px;"></span>
                                        Высокий спрос на объекты премиум-класса
                                    </li>
                                    <li style="display: flex; align-items: center; color: #4A5568;">
                                        <span style="background-color: #4F46E5; width: 8px; height: 8px; border-radius: 50%%; display: inline-block; margin-right: 15px;"></span>
                                        Оптимальное время для продажи
                                    </li>
                                </ul>
                            </div>

                            <!-- Our Services -->
                            <div style="margin-bottom: 35px;">
                                <h3 style="color: #2D3748; font-size: 18px; margin: 0 0 20px 0;">✨ Премиум-сервис</h3>
                                <div style="display: grid; gap: 15px;">
                                    <div style="background-color: #FFFFFF; border: 1px solid #E2E8F0; border-radius: 10px; padding: 20px;">
                                        <h4 style="color: #1A202C; margin: 0 0 10px 0; font-size: 16px;">🎯 Индивидуальный подход</h4>
                                        <p style="color: #4A5568; margin: 0; font-size: 14px;">Персональный менеджер и эксклюзивная стратегия продажи</p>
                                    </div>
                                    <div style="background-color: #FFFFFF; border: 1px solid #E2E8F0; border-radius: 10px; padding: 20px;">
                                        <h4 style="color: #1A202C; margin: 0 0 10px 0; font-size: 16px;">📸 Премиум-маркетинг</h4>
                                        <p style="color: #4A5568; margin: 0; font-size: 14px;">3D-туры, профессиональная фотосъемка и видеопрезентации</p>
                                    </div>
                                    <div style="background-color: #FFFFFF; border: 1px solid #E2E8F0; border-radius: 10px; padding: 20px;">
                                        <h4 style="color: #1A202C; margin: 0 0 10px 0; font-size: 16px;">⚡ Быстрая продажа</h4>
                                        <p style="color: #4A5568; margin: 0; font-size: 14px;">Доступ к базе проверенных премиум-покупателей</p>
                                    </div>
                                </div>
                            </div>

                            <!-- CTA Button -->
                            <div style="text-align: center; margin-bottom: 35px;">
                                <a href="#" style="display: inline-block; background: linear-gradient(135deg, #4F46E5 0%%, #7C3AED 100%%); color: #FFFFFF; padding: 15px 30px; text-decoration: none; border-radius: 8px; font-weight: 600; font-size: 16px; transition: transform 0.2s;">Получить оценку недвижимости</a>
                            </div>

                            <!-- Contact Info -->
                            <div style="background: linear-gradient(135deg, #F8FAFC 0%%, #EDF2F7 100%%); border-radius: 12px; padding: 25px; text-align: center;">
                                <h3 style="color: #2D3748; font-size: 18px; margin: 0 0 20px 0;">Свяжитесь с нами</h3>
                                <div style="display: grid; gap: 15px;">
                                    <p style="margin: 0; color: #4A5568;">
                                        <span style="display: block; font-weight: 600; color: #1A202C;">📱 Телефон</span>
                                        +7 (999) 123-45-67
                                    </p>
                                    <p style="margin: 0; color: #4A5568;">
                                        <span style="display: block; font-weight: 600; color: #1A202C;">✉️ Email</span>
                                        premium@realestate.com
                                    </p>
                                    <p style="margin: 0; color: #4A5568;">
                                        <span style="display: block; font-weight: 600; color: #1A202C;">📍 Офис</span>
                                        ул. Премиальная, 1
                                    </p>
                                </div>
                            </div>
                        </div>

                        <!-- Footer -->
                        <div style="background-color: #F8FAFC; padding: 20px; text-align: center; border-top: 1px solid #E2E8F0;">
                            <p style="color: #718096; font-size: 12px; margin: 0;">
                                © 2024 Premium Real Estate. Все права защищены.<br>
                                Если Вы хотите отписаться от рассылки, перейдите по <a href="#" style="color: #4F46E5; text-decoration: none;">этой ссылке</a>
                            </p>
                        </div>
                    </div>
                </body>
                </html>
                """, recipientName);
    }

    public static String getRegistrationTemplate(String recipientName, String registrationData) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <title>Добро пожаловать!</title>
                </head>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                        <h1 style="color: #2C3E50;">Добро пожаловать, %s!</h1>
                        <p>Спасибо за регистрацию в нашем сервисе.</p>
                        <div style="background-color: #F8F9FA; padding: 15px; border-radius: 5px;">
                            <h2 style="color: #2C3E50;">Ваши данные для входа:</h2>
                            %s
                        </div>
                        <p style="margin-top: 20px;">
                            Если у вас возникнут вопросы, не стесняйтесь обращаться в нашу службу поддержки.
                        </p>
                        <hr style="border: 1px solid #EEE; margin: 20px 0;">
                        <p style="font-size: 12px; color: #666;">
                            Это автоматическое письмо, пожалуйста, не отвечайте на него.
                        </p>
                    </div>
                </body>
                </html>
                """, recipientName, registrationData);
    }

    public static String getPasswordResetTemplate(String recipientName, String resetData) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <title>Восстановление пароля</title>
                </head>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                        <h1 style="color: #2C3E50;">Восстановление пароля</h1>
                        <p>Уважаемый(ая) %s,</p>
                        <p>Мы получили запрос на восстановление пароля для вашей учетной записи.</p>
                        <div style="background-color: #F8F9FA; padding: 15px; border-radius: 5px;">
                            %s
                        </div>
                        <p style="margin-top: 20px;">
                            Если вы не запрашивали восстановление пароля, пожалуйста, 
                            проигнорируйте это письмо или свяжитесь со службой поддержки.
                        </p>
                        <hr style="border: 1px solid #EEE; margin: 20px 0;">
                        <p style="font-size: 12px; color: #666;">
                            Это автоматическое письмо, пожалуйста, не отвечайте на него.
                        </p>
                    </div>
                </body>
                </html>
                """, recipientName, resetData);
    }

    public static String getAdvertisingTemplate(String recipientName) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <title>Специальное предложение</title>
                </head>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                        <h1 style="color: #2C3E50;">Специальное предложение</h1>
                        <p>Уважаемый(ая) %s,</p>
                        <div style="background-color: #F8F9FA; padding: 15px; border-radius: 5px;">
                            <h2 style="color: #2C3E50;">Наши лучшие предложения для вас:</h2>
                            <ul style="list-style-type: none; padding: 0;">
                                <li style="margin: 10px 0; padding: 10px; background-color: #fff; border-radius: 5px;">
                                    🏠 Премиальная недвижимость
                                </li>
                                <li style="margin: 10px 0; padding: 10px; background-color: #fff; border-radius: 5px;">
                                    💼 Инвестиционные возможности
                                </li>
                                <li style="margin: 10px 0; padding: 10px; background-color: #fff; border-radius: 5px;">
                                    🌟 Эксклюзивные предложения
                                </li>
                            </ul>
                        </div>
                        <p style="margin-top: 20px;">
                            Чтобы узнать больше, посетите наш сайт или свяжитесь с нашими специалистами.
                        </p>
                        <hr style="border: 1px solid #EEE; margin: 20px 0;">
                        <p style="font-size: 12px; color: #666;">
                            Вы получили это письмо, так как подписаны на нашу рассылку.
                            Чтобы отписаться, перейдите по <a href="#" style="color: #3498DB;">этой ссылке</a>.
                        </p>
                    </div>
                </body>
                </html>
                """, recipientName);
    }
} 